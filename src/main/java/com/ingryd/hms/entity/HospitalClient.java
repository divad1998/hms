package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "hospital_clients")
@Data
public class HospitalClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Hospital number cannot be blank.")
    @NotNull(message = "Hospital number is required.")
    @Length(max = 50, message = "Hospital number cannot exceed 50 characters.")
    private String hospitalNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public HospitalClient() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}