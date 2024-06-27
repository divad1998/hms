package com.ingryd.hms.controller;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UpdateUserDTO;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO userDTO){
        return userService.updateUser(id, userDTO);
    }

    @GetMapping("all")
    public ResponseEntity<Response> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("email")
    public ResponseEntity<Response> getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }
}
