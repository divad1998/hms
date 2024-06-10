package com.ingryd.hms.security;

import com.ingryd.hms.enums.Role;
import com.ingryd.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestRegistry -> requestRegistry                  
                        .requestMatchers(HttpMethod.POST, "/auth/patients/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/hospitals/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/staff/signup").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/auth/forgotten-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/email_verification").permitAll()
                        .requestMatchers(HttpMethod.POST, "/hospitals/{id}/patient-registration").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/appointments/{hospitalId}/request").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/consultations").hasAnyAuthority(Role.CONSULTANT.name())
                        .requestMatchers(HttpMethod.POST, "/appointments/{appointment_Id}/accept").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/consultants/consultant-specialties").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "consultants").hasAuthority(Role.PATIENT.name())
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
