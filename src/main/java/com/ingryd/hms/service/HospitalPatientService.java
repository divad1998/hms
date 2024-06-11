package com.ingryd.hms.service;

import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.HospitalPatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HospitalPatientService {
    private final HospitalPatientRepository hospitalPatientRepository;

    public void saveHospitalPatient(HospitalPatient hospitalPatient) {
        hospitalPatientRepository.save(hospitalPatient);
    }

    public HospitalPatient getHospitalPatient(Long user_id, Long hospitalPatientId, Long hospitalId) throws InvalidException {
        HospitalPatient hospitalPatient = hospitalPatientRepository.findByUser_IdAndIdAndHospital_Id(user_id, hospitalPatientId, hospitalId);
        if (hospitalPatient == null)
            throw new InvalidException("You aren't registered with the given hospital.");

        return hospitalPatient;
    }
}
