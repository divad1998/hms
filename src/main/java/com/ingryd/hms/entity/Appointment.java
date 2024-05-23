package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 255, message = "Reason cannot exceed 255 characters.")
    private String reason;

    private boolean emergency;

    private LocalDate preferredDate;

    private LocalTime preferredTime;

    @ManyToOne
    @JoinColumn(name = "hospital_client_id", nullable = false)
    private HospitalClient hospitalClient;

    @Column(length = 50)
    @NotBlank(message = "Consultant's specialty can't be blank.")
    @Length(max = 50, message = "Consultant's specialty can't exceed 50 characters.")
    private String consultantSpecialty;

    @ManyToOne
    @JoinColumn(name = "desired_staff_id")
    private Staff desiredConsultant;

    private boolean issued;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Appointment() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
