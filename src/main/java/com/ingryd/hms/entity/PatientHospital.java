package com.ingryd.hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "patient")
public class PatientHospital {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
    @ManyToOne
    @JoinColumn (name = "hospital_id", nullable = false)
    private Hospital hospital;
    @Column(nullable = false)
    private String registrationDate;

}
