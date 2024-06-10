package com.ingryd.hms.service;

import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.HospitalPatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HospitalPatientService {
    private final HospitalPatientRepository hospitalPatientRepository;


    public HospitalPatient getHospitalPatient(Long hospitalPatientId, Long hospitalId) throws InvalidException {
        HospitalPatient hospitalPatient = hospitalPatientRepository.findByUser_IdAndHospital_Id(hospitalPatientId, hospitalId);
        if (hospitalPatient == null)
            throw new InvalidException("You aren't registered with the given hospital.");
        return hospitalPatient;
    }
}
