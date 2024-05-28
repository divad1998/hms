package com.ingryd.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Response{
    private boolean status;
    private String message;
    private List<?> data = new ArrayList<>();
}