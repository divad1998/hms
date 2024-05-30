package com.ingryd.hms.service;

import com.ingryd.hms.dto.UserDTO;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public ResponseEntity<User> updateUser( Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User existingUser = optionalUser.get();

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setMiddleName(userDTO.getMiddleName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setContactAddress(userDTO.getContactAddress());
        existingUser.setCreatedAt(userDTO.getCreatedAt());
        User updatedUser = userRepository.save(existingUser);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
