package com.ingryd.hms.controller;

import com.ingryd.hms.entity.Staff;
import com.ingryd.hms.service.AppointmentService;
import com.ingryd.hms.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class Appointment {

    private final AppointmentService appointmentService;

    private final StaffService staffService;

    @Autowired
    public Appointment (AppointmentService appointmentService, StaffService staffService) {
        this.appointmentService = appointmentService;
        this.staffService = staffService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<com.ingryd.hms.entity.Appointment>> getAllAppointment(){
        return appointmentService.getAllAppointment();
    }

    @GetMapping("{id}")
    public ResponseEntity<com.ingryd.hms.entity.Appointment> getAppointmentById(@PathVariable long id){
        return appointmentService.findById(id);
    }

    @GetMapping("{hospital_id}")
    @PreAuthorize("hasRole('PATIENT')")
    List<Staff> getNullSpecialistConsultant (@PathVariable Long hospital_id) {
        return staffService.getNullSpecialistConsultant(hospital_id);
    }
}
