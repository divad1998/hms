package com.ingryd.hms.controller;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.ConfirmAppointmentDto;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PutMapping("/{id}/update")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody @Valid AppointmentDTO appointmentDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Appointment updateAppointment = appointmentService.updateAppointment(id, appointmentDto, userDetails.getUsername());

        return ResponseEntity.ok(updateAppointment);
    }
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

    @PostMapping("/confirm")
    public ResponseEntity<Appointment> confirmAppointment(@RequestBody ConfirmAppointmentDto confirmAppointmentDto,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        Appointment confirmAppointment =  appointmentService.confirmAppointment(confirmAppointmentDto, username);
         return ResponseEntity.ok(confirmAppointment);
    }

//    @PostMapping("/{hospital_Id}/request")
//    public ResponseEntity<Response> bookAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO, @PathVariable Long hospital_Id) throws InternalServerException {
//        return appointmentService.bookAppointment(appointmentDTO, hospital_Id);
//    }
}

