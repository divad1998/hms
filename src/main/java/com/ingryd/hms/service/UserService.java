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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
//        existingUser.setCreatedAt(userDTO.getCreatedAt());
        User updatedUser = userRepository.save(existingUser);

        //map to Dto before building response
        UpdateUserDTO responseUserDTO = mapper.mapToUserDto(updatedUser);
        Map<String, Object> map = new HashMap<>();
        map.put("user", responseUserDTO);
        Response response = new Response(true, "success", map);
        return ResponseEntity.ok(response);
        //return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
