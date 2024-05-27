package com.ingryd.hms.dto;

import com.ingryd.hms.enums.Role;
import jakarta.validation.constraints.*;
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

    @Size(min = 11, max = 11, message = "Max length of phone number is 11 digits.")
    private int phoneNumber;

    @NotBlank(message = "Email can't be blank.")
    @NotNull(message = "Email can't be null.")
    @Length(max = 50)
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password can't be blank.")
    @NotNull(message = "Password can't be null.")
    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~@$#!%^*?&()_+={}|:;])[A-Za-z\\d~@$#!%^*?&()_+={}|:;]{8,}$")
    private String password;

    @NotBlank(message = "Contact address can't be blank.")
    @NotNull(message = "Contact address can't be null.")
    private String contactAddress;

    private Role role;
    private LocalDateTime createdAt;
}
