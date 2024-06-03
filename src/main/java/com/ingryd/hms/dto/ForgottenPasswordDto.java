package com.ingryd.hms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class ForgottenPasswordDto {

    @Email(message = "Invalid Email.")
    @NotEmpty(message = "Email must not be empty.")
    private String email;
}
