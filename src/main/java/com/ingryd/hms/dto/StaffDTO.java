package com.ingryd.hms.dto;

import com.ingryd.hms.enums.Gender;
import com.ingryd.hms.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StaffDTO {

    @NotBlank(message = "First name cannot be blank.")
    @Length(max = 30, message = "First name cannot exceed 30 characters.")
    private String firstName;

    private String middleName;
    @NotBlank(message = "First name cannot be blank.")
    @Length(max = 30, message = "First name cannot exceed 30 characters.")
    private String lastName;

    @NotBlank(message = "Phone number cannot be blank.")
    @Length(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits.")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;
    private String password;
    private String contactAddress;

    private Gender gender;

    private Role role;

    @Length(max = 50, message = "Specialty cannot exceed 50 characters.")
    private String specialty;

    @Length(max = 30, message = "Level cannot exceed 30 characters.")
    private String level;

    @Length(max = 255, message = "Qualifications cannot exceed 255 characters.")
    private String qualifications;

    private Long userId;

    private Long hospitalId;
}
