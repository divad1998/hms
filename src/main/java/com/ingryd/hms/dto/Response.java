package com.ingryd.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

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

    public static Response build(boolean status, String message, String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return new Response(status, message, map);
    }
}
