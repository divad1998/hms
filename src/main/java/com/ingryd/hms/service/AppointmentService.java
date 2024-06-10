package com.ingryd.hms.service;

import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.*;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;
    private final HospitalPatientRepository hospitalPatientRepository;
    private final StaffRepository staffRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalService hospitalService;
    private final AuthService authService;
    private final StaffService staffService;

    public ResponseEntity<List<Appointment>> getAllAppointment(){
        return new ResponseEntity<>(appointmentRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Appointment> findById(long id){
        return new ResponseEntity<>(appointmentRepository.findById(id).get(), HttpStatus.OK);
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
            boolean confirmedAppointmentExists = appointmentRepository.findByPreferredDateAndPreferredTime(preferredDate, preferredTime).isConfirmed();
            if (confirmedAppointmentExists)
                throw new IllegalArgumentException("Sorry. Selected Date and Time already taken.");
        }

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
                .desiredConsultant(consultant)
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
