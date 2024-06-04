package com.ingryd.hms.service;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.entity.Hospital;
import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.repository.AppointmentRepository;
//import com.ingryd.hms.repository.HospitalClientRepository;
import com.ingryd.hms.repository.HospitalPatientRepository;
import com.ingryd.hms.repository.HospitalRepository;
import com.ingryd.hms.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final HospitalPatientRepository hospitalPatientRepository;
    private final StaffRepository staffRepository;
    private final HospitalRepository hospitalRepository;

    public ResponseEntity<Response> bookAppointment(AppointmentDTO appointmentDTO) {
        Optional<HospitalPatient> optionalPatient = hospitalPatientRepository.findById(appointmentDTO.getHospitalPatient().getId());
        if (optionalPatient.isEmpty()) {
            throw new RuntimeException("Hospital patient not found");
        }
        HospitalPatient hospitalPatient = optionalPatient.get();

        Optional<Hospital> existingHospital = hospitalRepository.findById(appointmentDTO.getHospital().getId());
        if (existingHospital.isEmpty()) {
            throw new RuntimeException("Hospital not found");
        }
        Hospital hospital = existingHospital.get();

        Staff desiredConsultant = staffRepository.findById(appointmentDTO.getDesiredConsultant().getId())
                .orElse(null);


        Appointment appointment = Appointment.builder()
                .reason(appointmentDTO.getReason())
                .emergency(appointmentDTO.isEmergency())
                .preferredDate(appointmentDTO.getPreferredDate())
                .preferredTime(appointmentDTO.getPreferredTime())
                .hospitalPatient(hospitalPatient)
                .consultantSpecialty(appointmentDTO.getConsultantSpecialty())
                .desiredConsultant(desiredConsultant)
                .acceptedByPatient(true)
                .hospital(hospital)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        appointmentRepository.save(appointment);

        Response response = new Response();
        response.setMessage("Appointment booked successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
