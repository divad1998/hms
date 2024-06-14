package com.ingryd.hms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table (name = "medicHistory")
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "patient_Id", nullable = false)
    private Long patientId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;


}
