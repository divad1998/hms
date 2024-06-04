package com.ingryd.hms.entity;

import com.ingryd.hms.enums.Gender;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.enums.Role;
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
    private String firstName;

    @Column(length = 30)
    private String middleName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private Profession profession;

    @Column(length = 50)
    private String specialty;

    @Column(length = 30)
    private String level;

    private String qualifications;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
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