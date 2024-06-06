package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "consultations")
@Data
@Builder
@AllArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospital_patient_id", nullable = false)
    private HospitalPatient hospitalPatient;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff; // should only be a CONSULTANT

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(length = 500)
    private String preDiagnosis;

    @Column(length = 1000)
    @NotBlank(message = "Tests to be ran can't be blank.")
    @Length(max = 1000, message = "Tests to be ran can't exceed 1000 characters.")
    private String testsToRun;

    @Column(length = 5000)
    @NotBlank(message = "Prescription can't be blank.")
    @Length(max = 5000, message = "Prescription can't exceed 5000 characters.")
    private String prescription;

    @Column(length = 500)
    private String diagnosis;

    @Column(length = 1000)
    @NotBlank(message = "The Referred can't be blank.")
    @Length(max = 1000, message = "The Referred can't exceed 1000 characters.")
    private String referredTo;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Consultation() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
