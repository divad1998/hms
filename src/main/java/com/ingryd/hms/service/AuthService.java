package com.ingryd.hms.service;

import com.ingryd.hms.dto.HttpResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    HttpResponseDto logout(Authentication authentication);
}
