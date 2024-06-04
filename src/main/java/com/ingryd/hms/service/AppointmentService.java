package com.ingryd.hms.service;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.ConfirmAppointmentDto;
import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.enums.Role;
import com.ingryd.hms.repository.AppointmentRepository;
import com.ingryd.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

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
            appointment.setDesiredConsultant(updatedAppointment.getDesiredConsultant());
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
}
