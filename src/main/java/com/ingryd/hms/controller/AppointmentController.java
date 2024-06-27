package com.ingryd.hms.controller;

import com.ingryd.hms.dto.AppointmentDTO;
import com.ingryd.hms.dto.ConfirmAppointmentDto;
import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UpdateAppointmentDTO;
import com.ingryd.hms.entity.Appointment;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/{hospital_Id}/{hospital_patient_id}/request")
    public ResponseEntity<Response> bookAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO, @PathVariable Long hospital_Id, @PathVariable Long hospital_patient_id) throws InternalServerException, InvalidException {
        return appointmentService.bookAppointment(appointmentDTO, hospital_Id, hospital_patient_id);
    }

    @PatchMapping("/{hospital_Id}/{hospital_patient_id}/{appointment_Id}/accept")
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

    @PutMapping("/{id}")
    public ResponseEntity<Response> consultantUpdateAppointment(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAppointmentDTO appointmentDto,
            @AuthenticationPrincipal User authUser) throws InvalidException {

        Appointment updateAppointment = appointmentService.consultantUpdateAppointment(id, appointmentDto, authUser);
        //response
        Map<String, Object> map = new HashMap<>();
        map.put("appointment", updateAppointment);
        Response response = new Response(true, "Successful.", map);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Response> confirmAppointment(@RequestBody ConfirmAppointmentDto confirmAppointmentDto,
                                                          @AuthenticationPrincipal User user) throws InvalidException {
        appointmentService.confirmAppointment(confirmAppointmentDto, user);
        Response response = new Response(true, "Appointment confirmed successfully.", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consultant_view")
    public ResponseEntity<Response> getAppointmentsOfConsultant() throws InternalServerException {
        return appointmentService.getAppointmentsOfConsultant();
    }

    @GetMapping("/{id}/consultant_view")
    public ResponseEntity<Response> consultantGetAppointmentById(@PathVariable long id) throws InternalServerException, InvalidException {
        return appointmentService.consultantGetAppointmentById(id);
    }

    @PutMapping("/{id}/{hospital_patient_id}")
    public ResponseEntity<Response> patientUpdateAppointment(@PathVariable Long id,
                                                             @PathVariable Long hospital_patient_id,
                                                             @RequestBody @Valid AppointmentDTO dto,
                                                             @AuthenticationPrincipal User authUser) throws InternalServerException, InvalidException {
        Appointment updatedAppointment = appointmentService.patientUpdateAppointment(id, hospital_patient_id, dto, authUser);
        //response
        Map<String, Object> map = new HashMap<>();
        map.put("appointment", updatedAppointment);
        Response response = new Response(true, "Successful.", map);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient_view")
    public ResponseEntity<Response> getAppointmentsOfPatient() throws InvalidException {
        List<Appointment> appointments = appointmentService.getAppointmentsOfPatient();
        Response response = Response.build(true, "Successful.","appointments", appointments);
        return ResponseEntity.ok(response);
    }
}