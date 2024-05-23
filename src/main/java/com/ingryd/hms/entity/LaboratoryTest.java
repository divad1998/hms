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
    @NotBlank(message = "Sample cannot be blank.")
    @NotNull(message = "Sample is required.")
    @Length(max = 50, message = "Sample cannot exceed 50 characters.")
    private String sample;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Lab unit cannot be blank.")
    @NotNull(message = "Lab unit is required.")
    @Length(max = 50, message = "Lab unit cannot exceed 50 characters.")
    private String labUnit;

    @Column(nullable = false)
    @NotBlank(message = "Investigation cannot be blank.")
    @NotNull(message = "Investigation is required.")
    @Length(max = 255, message = "Investigation can't exceed 255 characters.")
    private String investigation;

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "Result cannot be blank.")
    @NotNull(message = "Result is required.")
    @Length(max = 2000, message = "Result can't exceed 2000 characters")
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
