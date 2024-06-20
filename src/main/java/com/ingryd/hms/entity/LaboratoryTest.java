package com.ingryd.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "laboratory_tests")
@Data
public class LaboratoryTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @Column(nullable = false, length = 50)
    private String sample;

    @Column(nullable = false, length = 50)
    private String labUnit;

    @Column(nullable = false)
    private String investigation;

    @Column(nullable = false, length = 2000)
    private String result;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff; //validation should ensure this can only be a LAB_SCIENTIST

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public LaboratoryTest() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
