package com.ingryd.hms.controller;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/{hospital_Id}/request")
    public ResponseEntity<Response> bookAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO, @PathVariable Long hospital_Id) throws InternalServerException, InvalidException {
        return appointmentService.bookAppointment(appointmentDTO, hospital_Id);
    }

    @PostMapping("{hospital_Id}/{appointment_Id}/accept")
    public ResponseEntity<Response> acceptAppointment(@PathVariable Long hospital_Id, @PathVariable Long appointment_Id) throws InternalServerException, InvalidException {
        appointmentService.acceptAppointment(hospital_Id, appointment_Id);
        return ResponseEntity.ok(new Response(true, "Appointment accepted. Consultant will be notified.", null));
    }
}