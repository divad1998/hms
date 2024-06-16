package com.ingryd.hms.exceptionHandler;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.exception.InternalServerException;
import com.ingryd.hms.exception.InvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.status(422).body(new Response(false, e.getLocalizedMessage(), null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handle(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(422).body(new Response(false, e.getMessage(), null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Response> handle(SQLIntegrityConstraintViolationException e) {
        String msg = "Either the email|phone number|website|hfrn|hospital name (if present) has been registered already.";
        return ResponseEntity.status(422).body(new Response(false, msg, null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Response> handle(UsernameNotFoundException e) {
        return ResponseEntity.status(400).body(new Response(false, e.getMessage(), null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response> handle(AuthenticationException e) {
        return ResponseEntity.status(400).body(new Response(false, e.getMessage(), null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handle(IllegalArgumentException e) {
        return ResponseEntity.status(404).body(new Response(false, e.getMessage(), null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Response> handle(InternalServerException e) {
        return ResponseEntity.status(500).body(new Response(false, e.getMessage(), null));
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Response> handle(IllegalArgumentException e) {
//        return ResponseEntity.status(422).body(new Response(false, e.getMessage(), null));
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Response> handle(IllegalStateException e) {
        return ResponseEntity.status(422).body(new Response(false, e.getMessage(), null));
    }
//    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<Response> handle(IllegalStateException e) {
//        return ResponseEntity.status(422).body(new Response(false, e.getMessage(), null));
//    }


    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidException.class)
    public ResponseEntity<Response> handle(InvalidException e) {
        return ResponseEntity.status(422).body(new Response(false, e.getMessage(), null));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Response> handle(MissingServletRequestParameterException e) {
        return ResponseEntity.status(422).body(new Response(false, e.getMessage(), null));
    }
}
