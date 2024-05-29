package com.ingryd.hms.controller;

import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO){
        return userService.updateUser(id, userDTO);
    }

}
