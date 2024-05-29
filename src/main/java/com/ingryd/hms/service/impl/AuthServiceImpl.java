package com.ingryd.hms.service.impl;

import com.ingryd.hms.dao.UserDao;
import com.ingryd.hms.dto.HttpResponseDto;
import com.ingryd.hms.entity.User;
import com.ingryd.hms.exception.BadRequestException;
import com.ingryd.hms.exception.ForbiddenException;
import com.ingryd.hms.service.AuthService;
import com.ingryd.hms.util.Helper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Override
    public HttpResponseDto logout(Authentication authentication) {
        if (Helper.isEmpty(authentication)) {
            throw new ForbiddenException("Authentication is required");
        }

        User user = userDao.findByEmail(authentication.getName());
        if (Helper.isEmpty(user)) {
            throw new BadRequestException("Invalid User");
        }

        user.setLoggedIn(false);
        user.setLastLoginDate(LocalDateTime.now());
        userDao.save(user);

        // Clear the security context
        SecurityContextHolder.clearContext();

        return new HttpResponseDto("Successfully Logged out", HttpStatus.OK);
    }
}
