package com.ingryd.hms.service;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.ConfirmAppointmentDto;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.*;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.repository.*;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Builder
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HospitalPatientRepository hospitalPatientRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private AuthService authService;
    @Autowired
    private StaffService staffService;

    public Appointment confirmAppointment(ConfirmAppointmentDto confirmAppointmentDTO, String email) {
        Appointment appointment = appointmentRepository.findById(confirmAppointmentDTO.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getRole() != Role.ADMIN && user.getRole() != Role.CONSULTANT) {
            throw new RuntimeException("User does not have permission to confirm appointment");
        }

        appointment.setConfirmed(confirmAppointmentDTO.isConfirmed());
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, AppointmentDTO updatedAppointment, String email) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getRole() == Role.PATIENT) {
            // Patient can only update the reason field
            appointment.setReason(updatedAppointment.getReason());
        } else if (user.getRole() == Role.ADMIN || user.getRole() == Role.CONSULTANT) {
            // Admin and Consultant can update consultantSpecialty and desiredConsultant fields
            appointment.setConsultantSpecialty(updatedAppointment.getConsultantSpecialty());
//            appointment.setDesiredConsultant(updatedAppointment.getDesiredConsultant());
        } else {
            throw new RuntimeException("User does not have permission to update appointment");
        }

        if (updatedAppointment.getPreferredDate() != null) {
            appointment.setPreferredDate(updatedAppointment.getPreferredDate());
        }
        if (updatedAppointment.getPreferredTime() != null) {
            appointment.setPreferredTime(updatedAppointment.getPreferredTime());
        }

        return appointmentRepository.save(appointment);


    }
    public ResponseEntity<Response> bookAppointment(AppointmentDTO appointmentDTO, Long hospitalId) throws InternalServerException {
        //hospital validation
        Hospital hospital = hospitalService.validateHospital(hospitalId);
        //is patient registered with hospital?
        User authUser = authService.getAuthUser();
        HospitalPatient hospitalPatient = hospitalPatientRepository.findByUser_IdAndHospital_Id(authUser.getId(), hospital.getId());
        if (hospitalPatient == null)
            throw new IllegalArgumentException("You aren't registered with the given hospital.");

        //validate date and time
        LocalDate preferredDate = appointmentDTO.getPreferredDate();
        LocalTime preferredTime = appointmentDTO.getPreferredTime();
        if (preferredTime != null && preferredDate == null)
            throw new IllegalArgumentException("Time selected but Date is null.");
        if (preferredDate != null && preferredTime != null) {
            Optional<Appointment> existingAppointment = appointmentRepository.findByPreferredDateAndPreferredTime(preferredDate, preferredTime);
            if (existingAppointment.isPresent() && existingAppointment.get().isConfirmed())
                throw new IllegalArgumentException("Sorry. Selected Date and Time already taken.");
        }

        //does hospital have consultants?
        List<Staff> consultants = staffService.getAllConsultantsOfAHospital(hospital.getId());
        long enabledConsultantCount = consultants.stream()
                                                .filter(consultant -> consultant.getUser().isEnabled())
                                                .count();

        if (enabledConsultantCount == 0)
            throw new IllegalArgumentException("Couldn't find any consultants in the hospital who are currently fully registered on this platform.");
        //ToDo: refactor exception type?

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
}
//
//    public void bookAppointment() {
//    }
//
//    public void bookAppointment() {
//    }