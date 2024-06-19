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
    private String testsToRun;

    @Column(length = 5000)
    private String prescription;

    @Column(length = 500)
    private String diagnosis;

    @Column(length = 1000)
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
