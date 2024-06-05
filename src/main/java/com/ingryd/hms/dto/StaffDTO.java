package com.ingryd.hms.dto;

import com.ingryd.hms.enums.Gender;
import com.ingryd.hms.enums.Profession;
import com.ingryd.hms.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StaffDTO {

    @NotBlank(message = "First name cannot be blank.")
    @NotNull(message = "First name is required.")
    @Length(max = 30, message = "First name cannot exceed 30 characters.")
    private String firstName;

    @Length(max = 20, message = "Middle name cannot exceed 30 characters.")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank.")
    @NotNull(message = "Last name is required.")
    @Length(max = 30, message = "Last name cannot exceed 30 characters.")
    private String lastName;

    @NotBlank(message = "Phone number cannot be blank.")
    @NotNull(message = "Last name is required.")
    @Length(min = 11, max = 11, message = "Phone number must be 11 digits.")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank.")
    @NotNull(message = "Email is required.")
    @Length(max = 50, message = "Email cannot exceed 50 characters.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password can't be blank.")
    @NotNull(message = "Password can't be null.")
    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~@$#!%^*?&()_+={}|:;])[A-Za-z\\d~@$#!%^*?&()_+={}|:;]{8,}$",
            message = "Password must contain at least one capitalized alphabet, small alphabet, one digit, and one special character.")
    private String password;

    @NotBlank(message = "Contact address can't be blank.")
    @NotNull(message = "Contact address can't be null.")
    @Length(max = 255, message = "Contact address can only contain 255 characters.")
    private String contactAddress;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private Profession profession;

    @Length(max = 50, message = "Specialty cannot exceed 50 characters.")
    private String specialty;

    @Length(max = 30, message = "Level cannot exceed 30 characters.")
    private String level;

    @Length(max = 255, message = "Qualifications cannot exceed 255 characters.")
    private String qualifications;

//    private Long userId;

//    private Long hospitalId;
}
