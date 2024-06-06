package com.ingryd.hms.controller;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.exception.InternalServerException;
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

    @PostMapping("/{hospitalId}/request")
    public ResponseEntity<Response> bookAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO, @PathVariable Long hospitalId) throws InternalServerException {
        return appointmentService.bookAppointment(appointmentDTO, hospitalId);
    }

    @PostMapping("{appointment_Id}/accept")
    public ResponseEntity<Response> acceptAppointment(@PathVariable Long appointment_Id, @RequestParam("accepted") boolean accepted) {
        return appointmentService.acceptAppointment(appointment_Id, accepted);
    }
}