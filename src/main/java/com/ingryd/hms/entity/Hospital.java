package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "hospitals")
@Data
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Hospital name can't be blank.")
    @NotNull(message = "Hospital name can't be null.")
    @Length(max = 100, message = "Max length of hospital name is 100 characters.")
    private String hospitalName;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Hospital's branch can't be blank.")
    @NotNull(message = "Hospital's branch can't be null.")
    @Length(max = 100, message = "Max length of hospital's branch is 100 characters.")
    private String branch;

    @Column(nullable = false)
    @NotBlank(message = "Hospital's address can't be blank.")
    @NotNull(message = "Hospital's address can't be null.")
    @Length(max = 255, message = "Max length of hospital's address is 255 characters.")
    private String address;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "City can't be blank.")
    @NotNull(message = "City can't be null.")
    @Length(max = 50, message = "Max length of city is 50 characters.")
    private String city;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "State can't be blank.")
    @NotNull(message = "State can't be null.")
    @Length(max = 50, message = "Max length of state is 50 characters.")
    private String state;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Country can't be blank.")
    @NotNull(message = "Country can't be null.")
    @Length(max = 50, message = "Max length of country is 50 characters.")
    private String country;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "HFRN can't be blank.")
    @NotNull(message = "HFRN can't be null.")
    @Length(max=255, message = "Max length of hfrn is 255 characters.")
    private String hfrn;

    @Column(nullable = false, unique = true, length = 11)
    @Length(min = 11, max = 11, message = "Max length of phone number is 11 digits.")
    private String contactNumber;

    @Column(nullable = false, length = 50, unique = true)
    @NotBlank(message = "Email can't be blank.")
    @NotNull(message = "Email can't be null.")
    @Length(max = 50, message = "Max length of email is 50 characters.")
    @Email(message = "Invalid email format.")
    private String email;

    @Column(length = 40, unique = true)
    @Length(max = 40, message = "Max length of website is 40 characters.")
    private String website;

    @OneToOne
    @JoinColumn(name = "registered_by", nullable = false)
    private User registeredBy;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Hospital() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
