package com.ingryd.hms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class UpdateUserDTO {
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

    @NotBlank(message = "Contact address can't be blank.")
    @NotNull(message = "Contact address can't be null.")
    private String contactAddress;

    private LocalDateTime createdAt;
}
