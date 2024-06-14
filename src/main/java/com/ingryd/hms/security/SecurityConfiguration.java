package com.ingryd.hms.security;

import com.ingryd.hms.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class    SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestRegistry -> requestRegistry                  
                        .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/appointments/{id}/update").hasAnyAuthority(Role.ADMIN.name(), Role.CONSULTANT.name())
                        .requestMatchers(HttpMethod.POST, "/appointments/confirm").hasAnyAuthority(Role.ADMIN.name(), Role.CONSULTANT.name())
                        .requestMatchers(HttpMethod.POST, "/auth/patients/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/hospitals/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/staff/signup").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/auth/forgotten_password/{email}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset_password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/email_verification/{token}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/hospitals/{id}/patient-registration").hasAuthority(Role.PATIENT.name())

                        .requestMatchers(HttpMethod.POST, "/hospitals/{id}/patient-registration/hms").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/appointments/{hospital_Id}/request").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/consultations").hasAnyAuthority(Role.CONSULTANT.name())
                        .requestMatchers(HttpMethod.GET, "/medical-history/patient/{patientId}").hasAnyAuthority(Role.PATIENT.name(), Role.CONSULTANT.name())

                        .requestMatchers(HttpMethod.POST, "/hospitals/{id}/patient-registration/hmo").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/appointments/{hospitalId}/{hospital_patient_id}/request").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/consultations").hasAnyAuthority(Role.CONSULTANT.name())
                        .requestMatchers(HttpMethod.PATCH, "/appointments/{hospital_Id}/{hospital_patient_id}/{appointment_Id}/accept").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.PATCH, "/appointments/{hospital_Id}/{hospital_patient_id}/{appointment_Id}/cancel").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "/consultants/consultant-specialties").hasAuthority(Role.PATIENT.name())
                        .requestMatchers(HttpMethod.POST, "consultants").hasAuthority(Role.PATIENT.name())

                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
