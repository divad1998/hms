package com.ingryd.hms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class HttpResponseDto {

    private String message;

    private HttpStatus statusCode;

    private String entity;

    private Object object;

    private ResponseEntity<?> data;

    private int limit;

    private int page;

    private int total;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate timestamp = LocalDate.now();

//    public HttpResponseDto(String message, HttpStatus statusCode, String entity,
//                           ResponseEntity<?> data, Object object) {
//        this.message = message;
//        this.statusCode = statusCode;
//        this.entity = entity;
//        this.data = data;
//        this.object = object;
//    }
//
//    public HttpResponseDto(String message, HttpStatus statusCode, String entity,
//                           ResponseEntity<?> data) {
//        this.message = message;
//        this.statusCode = statusCode;
//        this.entity = entity;
//        this.data = data;
//    }
//
//    public HttpResponseDto(String message, HttpStatus statusCode, String entity,
//                           ResponseEntity<?> data, int total) {
//        this.message = message;
//        this.statusCode = statusCode;
//        this.entity = entity;
//        this.data = data;
//        this.total = total;
//    }

//    public HttpResponseDto(String message, HttpStatus statusCode, String entity,
//                           ResponseEntity<?> data, int total, int page, int limit) {
//        this.message = message;
//        this.statusCode = statusCode;
//        this.entity = entity;
//        this.data = data;
//        this.total = total;
//        this.page = page;
//        this.limit = limit;
//    }

    public HttpResponseDto(String message, HttpStatus statusCode, String entity) {
        this.message = message;
        this.statusCode = statusCode;
        this.entity = entity;
    }

//    public HttpResponseDto(String message, HttpStatus statusCode, String entity,
//                           Object obj) {
//        this.message = message;
//        this.statusCode = statusCode;
//        this.entity = entity;
//        this.object = obj;
//    }

//    public HttpResponseDto(String message) {
//        this.message = message;
//    }

    public HttpResponseDto(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}

