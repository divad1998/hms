package com.ingryd.hms.controller;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("book")
    public ResponseEntity<Response> bookAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO){
        return appointmentService.bookAppointment(appointmentDTO);
    }
}
