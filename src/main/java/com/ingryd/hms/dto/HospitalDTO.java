package com.ingryd.hms.dto;

import com.ingryd.hms.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class HospitalDTO {

    @NotBlank(message = "First name can't be blank.")
    @NotNull(message = "First name can't be null.")
    private String registrant_firstName;

    @NotBlank(message = "Last name can't be blank.")
    @NotNull(message = "Last name can't be null.")
    private String registrant_lastName;

    @NotBlank(message = "Hospital name can't be blank.")
    @NotNull(message = "Hospital name can't be null.")
    @Length(max = 100, message = "Max length of hospital name is 100 characters.")
    private String hospitalName;

    @NotBlank(message = "Hospital's branch can't be blank.")
    @NotNull(message = "Hospital's branch can't be null.")
    @Length(max = 100, message = "Max length of hospital's branch is 100 characters.")
    private String branch;

    @NotBlank(message = "Hospital's address can't be blank.")
    @NotNull(message = "Hospital's address can't be null.")
    @Length(max = 255, message = "Max length of hospital's address is 255 characters.")
    private String address;

    @NotBlank(message = "City can't be blank.")
    @NotNull(message = "City can't be null.")
    @Length(max = 50, message = "Max length of city is 50 characters.")
    private String city;

    @NotBlank(message = "State can't be blank.")
    @NotNull(message = "State can't be null.")
    @Length(max = 50, message = "Max length of state is 50 characters.")
    private String state;

    @NotBlank(message = "Country can't be blank.")
    @NotNull(message = "Country can't be null.")
    @Length(max = 50, message = "Max length of country is 50 characters.")
    private String country;

    @NotBlank(message = "HFRN can't be blank.")
    @NotNull(message = "HFRN can't be null.")
    @Length(max=255, message = "Max length of hfrn is 255 characters.")
    private String hfrn;

    @Length(min = 11, max = 11, message = "Max length of phone number is 11 digits.")
    private String contactNumber;

    @Length(max = 40, message = "Max length of website is 40 characters.")
    private String website;

    @NotBlank(message = "Email can't be blank.")
    @NotNull(message = "Email can't be null.")
    @Length(max = 50, message = "Max length of email is 50 characters.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password can't be blank.")
    @NotNull(message = "Password can't be null.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~@$#!%^*?&()_+={}|:;])[A-Za-z\\d~@$#!%^*?&()_+={}|:;]{8,}$",
            message = "Password must contain at least one capitalized alphabet, small alphabet, one digit, and one special character.")
    private String password;

    private LocalDateTime createdAt;
}
