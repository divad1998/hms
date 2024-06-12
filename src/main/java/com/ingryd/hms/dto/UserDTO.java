package com.ingryd.hms.dto;

import com.ingryd.hms.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "First name can't be blank.")
    @NotNull(message = "First name can't be null.")
    @Length(max = 30, message = "Max length of first name is 30 characters.")
    private String firstName;

    @NotBlank(message = "Middle name can't be blank.")
    @Length(max = 30, message = "Max length is middle name is 30 characters.")
    private String middleName;

    @NotBlank(message = "Last name can't be blank.")
    @NotNull(message = "Last name can't be null.")
    @Length(max = 30, message = "Max length of last name is 30 characters.")
    private String lastName;

    @Length(min = 11, max = 11, message = "Max length of phone number is 11 digits.")
    private String phoneNumber;

    @NotBlank(message = "Email can't be blank.")
    @NotNull(message = "Email can't be null.")
    @Length(max = 50)
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

    private Role role;
    private LocalDateTime createdAt;
}
