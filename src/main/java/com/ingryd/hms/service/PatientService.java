package com.ingryd.hms.service;

import com.ingryd.hms.dto.RegisterPatientDto;
import org.springframework.stereotype.Service;

public interface PatientService {

    RegisterPatientDto registerUser(RegisterPatientDto dto);
}
