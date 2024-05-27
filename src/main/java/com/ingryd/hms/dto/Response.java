package com.ingryd.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Response extends RepresentationModel<Response> {
    private boolean status;
    private String message;
    private List<?> date = new ArrayList<>();
}
