package com.ingryd.hms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity(name = "tokens")
@Data
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 4)
//    @Length(max = 4, message = "Max length of value is 4 characters.")
    private int value;

    @ManyToOne(optional = false)
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
