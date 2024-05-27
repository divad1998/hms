package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "consultations")
@Data
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospital_client_id", nullable = false)
    private HospitalPatient hospitalClient;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff; // should only be a CONSULTANT

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "Comment can't be blank.")
    @NotNull(message = "A comment is required.")
    @Length(max = 1000, message = "Comment cannot exceed 1000 characters.")
    private String comment;

    @Column(length = 100)
    @NotBlank(message = "Preliminary diagnosis can't be blank.")
    @Length(max = 100, message = "Preliminary diagnosis cannot exceed 100 characters.")
    private String preDiagnosis;

    @Column(length = 1000)
    @NotBlank(message = "Tests to be ran can't be blank.")
    @Length(max = 1000, message = "Tests to be ran can't exceed 1000 characters.")
    private String testsToRun;

    @Column(length = 5000)
    @NotBlank(message = "Prescription can't be blank.")
    @Length(max = 5000, message = "Prescription can't exceed 5000 characters.")
    private String prescription;

    private boolean medicationDispensed;

    @Column(length = 1000)
    @NotBlank(message = "The Referred can't be blank.")
    @Length(max = 1000, message = "The Referred can't exceed 1000 characters.")
    private String referredTo;

    private boolean completed;

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
