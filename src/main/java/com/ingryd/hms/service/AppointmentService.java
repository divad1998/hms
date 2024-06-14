package com.ingryd.hms.service;

import com.ingryd.hms.controller.AppointmentController;
import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.AppointmentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.*;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.repository.*;
//import com.ingryd.hms.repository.HospitalClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalService hospitalService;
    private final AuthService authService;
    private final StaffService staffService;
    private final HospitalPatientService hospitalPatientService;

    public ResponseEntity<Response> bookAppointment(AppointmentDTO appointmentDTO, Long hospitalId, Long hospitalPatient_id) throws InternalServerException, InvalidException {
        //hospital validation
        Hospital hospital = hospitalService.validateHospital(hospitalId);
        //is patient registered with hospital?
        User authUser = authService.getAuthUser();
        HospitalPatient hospitalPatient = hospitalPatientService.getHospitalPatient(authUser.getId(), hospitalPatient_id, hospital.getId());
        //validate date and time
        LocalDate preferredDate = appointmentDTO.getPreferredDate();
        LocalTime preferredTime = appointmentDTO.getPreferredTime();
        if (preferredTime != null && preferredDate == null)
            throw new IllegalArgumentException("Time selected but Date is null.");
        //ToDo: fix this
        //        if (preferredDate != null && preferredTime != null) {
//            boolean confirmedAppointmentExists = appointmentRepository.findByPreferredDateAndPreferredTime(preferredDate, preferredTime).isConfirmed();
//            if (confirmedAppointmentExists)
//                throw new IllegalArgumentException("Sorry. Selected Date and Time already taken.");
//        }
        //ToDo: has preferred_date passed?

        //does hospital have consultants?
        List<Staff> consultants = staffService.getAllConsultantsOfAHospital(hospital.getId());
        long enabledConsultantCount = consultants.stream()
                .filter(consultant -> consultant.getUser().isEnabled())
                .count();

        if (enabledConsultantCount == 0)
            throw new IllegalArgumentException("Couldn't find any consultants in the hospital who are currently fully registered on this platform."); //ToDo: refactor exception type?

        //validate consultant specialty
        String specialty = appointmentDTO.getConsultantSpecialty();
        if (specialty != null) {
            if (staffService.getConsultantsBySpecialty(specialty).isEmpty())
                throw new IllegalArgumentException("Invalid consultant specialty.");
        }
        //validate consultant id
        Long consultantId = appointmentDTO.getDesiredConsultantId();
        Staff consultant = null;
        if (consultantId != 0) {
            consultant = staffService.getConsultantById(consultantId);
            if (consultant == null)
                throw new IllegalArgumentException("Invalid Consultant.");
            if (!consultant.getUser().isEnabled())
                throw new IllegalArgumentException("Selected Consultant isn't registered fully on this platform.");
        }

        Appointment appointment = Appointment.builder()
                .reason(appointmentDTO.getReason())
                .emergency(appointmentDTO.isEmergency())
                .preferredDate(appointmentDTO.getPreferredDate())
                .preferredTime(appointmentDTO.getPreferredTime())
                .hospitalPatient(hospitalPatient)
                .consultantSpecialty(appointmentDTO.getConsultantSpecialty())
                .staff(consultant)
                .acceptedByPatient(true)
                .hospital(hospital)
                .build();

        appointmentRepository.save(appointment);
        //ToDo: send in-app notifications to consultants
        //build response
        Response response = new Response();
        response.setMessage("Appointment requested successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public void acceptAppointment(Long hospitalId, Long hospital_patient_id, Long appointmentId) throws InvalidException, InternalServerException {
        //validate appointment
        User authUser = authService.getAuthUser();
        HospitalPatient hospitalPatient = hospitalPatientService.getHospitalPatient(authUser.getId(), hospital_patient_id, hospitalId);
        Appointment appointment = validateAppointment(appointmentId, hospitalPatient.getId(), hospitalPatient.getHospital().getId());
        if (appointment.getPreferredDate() == null)
            throw new InvalidException("Error. Appointment has no date.");
        if (appointment.isAcceptedByPatient() || appointment.isConfirmed() || appointment.isCancelled())
            throw new InvalidException("Error. Appointment has either been accepted previously, confirmed, or cancelled. ");
        String specialty = appointment.getConsultantSpecialty();
        validateSpecialtyInAppointment(specialty, hospitalPatient.getHospital().getId());

        Staff consultant = appointment.getStaff();
        if (consultant != null) {
            List<Appointment> existingAppointments = appointmentRepository.findByStaff_idAndPreferredDateAndPreferredTime(consultant.getId(), appointment.getPreferredDate(), appointment.getPreferredTime());
           if (!existingAppointments.isEmpty()) {
               for (Appointment appt : existingAppointments) {
                   if (appt.isConfirmed()) {
                       throw new InvalidException("Consultant already has an Appointment scheduled for that date.");
                   }
               }
           }
        } else {
            throw new InvalidException("Error. No Consultant specified in Appointment.");
        }

        //accept
        appointment.setAcceptedByPatient(true);
        appointmentRepository.save(appointment);

        //ToDo: send in-app notif to Related consultant
        }

    public ResponseEntity<Response> consultantGetAppointmentById(long id) throws InvalidException, InternalServerException {
        //Test cases:
        //endpoint; consultant; needs to belong to the consultant or no consultant, and hospital; if invalid
        //validate consultant entity associated with authenticated user
        Staff consultant = staffService.validateAuthenticatedConsultant();
        //validate related hospital of consultant
        Hospital hospital = hospitalService.validateConsultantHospital(consultant);
        //validate appointment
        Appointment appointment = appointmentRepository.findByIdAndHospital_Id(id, hospital.getId());
        if (appointment == null)
            throw new InvalidException("Invalid appointment.");
        if (appointment.getConsultantSpecialty() != null && !appointment.getConsultantSpecialty().equals(consultant.getSpecialty()))
            throw new InvalidException("You can't view this appointment.");
        if (appointment.getStaff() != null && !appointment.getStaff().getId().equals(consultant.getId()))
            throw new InvalidException("You can't view this appointment.");

        //build response
        Map<String, Object> map = new HashMap<>();
        map.put("appointment", appointment);
        Response response = new Response(true, "Successful.", map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> getAppointmentsOfConsultant() throws InternalServerException {
        //Test cases:
        //endpoint; consultant only; if appointments is null; only view appointment requests for himself/herself, or those with null specialty and desiredStaff of your hospital; response:OK
        //validate authenticated consultant user
        Staff consultant = staffService.validateAuthenticatedConsultant();
        //validate associated hospital
        Hospital hospital = hospitalService.validateConsultantHospital(consultant);
        //fetch and validate appointments
        List<Appointment> appointments = appointmentRepository.findByHospital_Id(hospital.getId());
        if (appointments.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("appointments", new HashMap<>());
            return new ResponseEntity<>(new Response(true, "Successful.", map), HttpStatus.OK);
        }
        Set<Appointment> appointmentSet = appointments
                                                .stream()
                                                .filter(appointment -> (appointment.getConsultantSpecialty() == null && appointment.getStaff() == null) //appt specialty is not null or null; appointment desired staff is not null or null
                                                                || (appointment.getStaff() != null && appointment.getStaff().getId().equals(consultant.getId()))
                                                                || (appointment.getConsultantSpecialty() != null && appointment.getConsultantSpecialty().equals(consultant.getSpecialty()))
                                                        )
                                                .collect(Collectors.toSet());

        //build response
        Map<String, Object> map = new HashMap<>();
        map.put("appointments", appointments);
        Response response = new Response(true, "Successful.", map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Checks whether Appointment is valid for the hospital patient at the hospital.
     * @param appointmentId
     * @param hospitalPatientId
     * @param hospital_Id
     * @return
     * @throws InvalidException
     */
    private Appointment validateAppointment(Long appointmentId, Long hospitalPatientId, Long hospital_Id) throws InvalidException {
        Appointment appointment = appointmentRepository.findByIdAndHospitalPatient_idAndHospital_id(appointmentId, hospitalPatientId, hospital_Id);
        if (appointment==null)
            throw new InvalidException("Invalid Appointment.");
        return appointment;
    }

    private void validateSpecialtyInAppointment(String specialty, Long hospital_Id) throws InternalServerException {
        if (specialty != null) {
            boolean validSpecialty = staffService.getAllConsultantSpecialties(hospital_Id).contains(specialty.toLowerCase());
            if (!validSpecialty) {
                log.error("Consultant specialty in Appointment is invalid.");
                throw new InternalServerException("Server error. Kindly contact support.");
            }
        }
    }

    /**
     * handles a patient's cancellation of an appointment.
     */
    public void cancelAppointment(Long hospital_id, Long hospital_patient_id, Long appointment_Id) throws InvalidException {
        //validate appointment
        User authUser = authService.getAuthUser();
        HospitalPatient hospitalPatient = hospitalPatientService.getHospitalPatient(authUser.getId(), hospital_patient_id, hospital_id);
        Appointment appointment = validateAppointment(appointment_Id, hospitalPatient.getId(), hospitalPatient.getHospital().getId());
        if (appointment.isCancelled())
            throw new InvalidException("Error. Appointment is already cancelled.");
        //cancel appointment
        appointment.setCancelled(true);
        appointmentRepository.save(appointment);
        //TODO: Send in-app notif to consultant
    }
}
