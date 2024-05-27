package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "staff")
@Data
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "First name cannot be blank.")
    @NotNull(message = "First name is required.")
    @Length(max = 30, message = "First name cannot exceed 30 characters.")
    private String firstName;

    @Column(length = 30)
    @Length(max = 20, message = "Middle name cannot exceed 30 characters.")
    private String middleName;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Last name cannot be blank.")
    @NotNull(message = "Last name is required.")
    @Length(max = 30, message = "Last name cannot exceed 30 characters.")
    private String lastName;

    @Column(nullable = false, length = 11)
    @Size(min = 11, max = 11, message = "Phone number must be 11 digits.")
    private int phoneNumber;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Email cannot be blank.")
    @NotNull(message = "Email is required.")
    @Length(max = 50, message = "Email cannot exceed 50 characters.")
    @Email(message = "Invalid email format.")
    private String email;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Role cannot be blank.")
    @NotNull(message = "Role is required.")
    @Length(max = 50, message = "Role cannot exceed 50 characters.")
    private String role;

    @Column(length = 50)
    @Length(max = 50, message = "Specialty cannot exceed 50 characters.")
    private String specialty;

    @Column(length = 30)
    @Length(max = 30, message = "Level cannot exceed 30 characters.")
    private String level;

    @Length(max = 255, message = "Qualifications cannot exceed 255 characters.")
    private String qualifications;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Staff() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}