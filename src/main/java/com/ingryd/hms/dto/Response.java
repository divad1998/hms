package com.ingryd.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response extends RepresentationModel<Response> {
    private boolean status;
    private String message;
    private Map<String, Object> data = new HashMap<>();
}
