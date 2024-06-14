package com.ingryd.hms.controller;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/{hospital_Id}/{hospital_patient_id}/request")
    public ResponseEntity<Response> bookAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO, @PathVariable Long hospital_Id, @PathVariable Long hospital_patient_id) throws InternalServerException, InvalidException {
        return appointmentService.bookAppointment(appointmentDTO, hospital_Id, hospital_patient_id);
    }

    @PatchMapping("{hospital_Id}/{hospital_patient_id}/{appointment_Id}/accept")
    public ResponseEntity<Response> acceptAppointment(@PathVariable Long hospital_Id, @PathVariable Long hospital_patient_id, @PathVariable Long appointment_Id) throws InternalServerException, InvalidException {
        appointmentService.acceptAppointment(hospital_Id, hospital_patient_id, appointment_Id);
        return ResponseEntity.ok(new Response(true, "Appointment accepted. Consultant will be notified.", null));
    }

    @PatchMapping("{hospital_Id}/{hospital_patient_id}/{appointment_Id}/cancel")
    public ResponseEntity<Response> cancelAppointment(@PathVariable Long hospital_Id, @PathVariable Long hospital_patient_id, @PathVariable Long appointment_Id) throws InvalidException {
        appointmentService.cancelAppointment(hospital_Id, hospital_patient_id, appointment_Id);
        Response response = new Response(true, "Appointment cancelled successfully.", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consultant_view")
    public ResponseEntity<Response> getAppointmentsOfConsultant() throws InternalServerException {
        return appointmentService.getAppointmentsOfConsultant();
    }

    @GetMapping("{/id/consultant_view}")
    public ResponseEntity<Response> consultantGetAppointmentById(@PathVariable long id) throws InternalServerException, InvalidException {
        return appointmentService.consultantGetAppointmentById(id);
    }
}