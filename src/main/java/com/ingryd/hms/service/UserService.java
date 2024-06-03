package com.ingryd.hms.service;

import com.ingryd.hms.dto.Response;
import com.ingryd.hms.dto.UpdateUserDTO;
import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.mapper.Mapper;
import com.ingryd.hms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper = Mapper.mapper;

    public ResponseEntity<Response> updateUser(Long id, UpdateUserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            Response response = new Response(false, "Invalid user.", null);
            return ResponseEntity.status(404).body(response);
        }
        User existingUser = optionalUser.get();

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setMiddleName(userDTO.getMiddleName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setContactAddress(userDTO.getContactAddress());

        User updatedUser = userRepository.save(existingUser);

        UpdateUserDTO responseUserDTO = mapper.mapToUserDto(updatedUser);
        Map<String, Object> map = new HashMap<>();
        map.put("user", responseUserDTO);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<Response> getAllUsers() {
        List<User> user = userRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("users", user);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            Response response = new Response(false, "User not found.", null);
            return ResponseEntity.status(404).body(response);
        }
        User user = optionalUser.get();
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email);
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            Response response = new Response(true, "success", map);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
            Response response = new Response(false, "User not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }
}
