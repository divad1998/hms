package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

@Entity(name = "appointments")
@Data
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String reason;

    private boolean emergency;

    private LocalDate preferredDate;

    private LocalTime preferredTime;

    @ManyToOne
    @JoinColumn(name = "hospital_client_id", nullable = false)
    private HospitalPatient hospitalPatient;

    @Column(length = 50)
    @Length(max = 50, message = "Consultant's specialty can't exceed 50 characters.")
    private String consultantSpecialty;

    @ManyToOne
    @JoinColumn(name = "desired_staff_id")
    private Staff staff;

    private boolean confirmed;
    private boolean acceptedByPatient;
    private boolean cancelled;

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