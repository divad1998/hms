package com.ingryd.hms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerificationDTO {
    @NotNull(message = "Value cannot be null.")
    private int value;
}
