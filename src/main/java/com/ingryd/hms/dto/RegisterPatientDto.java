package com.ingryd.hms.dto;

import com.ingryd.hms.object.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "patient")
@Data
public class RegisterPatientDto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "First name can't be blank.")
    @NotNull(message = "First name can't be null.")
    @Length(max = 30, message = "Max length of first name is 30 characters.")
    private String firstName;

    @Column(length = 30)
    @NotBlank(message = "Middle name can't be blank.")
    @Length(max = 30, message = "Max length is middle name is 30 characters.")
    private String middleName;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "Last name can't be blank.")
    @NotNull(message = "Last name can't be null.")
    @Length(max = 30, message = "Max length of last name is 30 characters.")
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(min = 11, max = 11, message = "Max length of phone number is 11 digits.")
    private int phoneNumber;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Email can't be blank.")
    @NotNull(message = "Email can't be null.")
    @Length(max = 50)
    @Email(message = "Invalid email format.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password can't be blank.")
    @NotNull(message = "Password can't be null.")
    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~@$#!%^*?&()_+={}|:;])[A-Za-z\\d~@$#!%^*?&()_+={}|:;]{8,}$")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Contact address can't be blank.")
    @NotNull(message = "Contact address can't be null.")
    private String contactAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private boolean isLoggedIn;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime lastLoginDate;

    private LocalDateTime updatedAt;

    public RegisterPatientDto() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    }

