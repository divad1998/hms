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

    public HospitalPatient getHospitalPatient(Long hospital_patient_id, Long user_Id) throws InvalidException {
        HospitalPatient hospitalPatient = hospitalPatientRepository.findByIdAndUser_Id(hospital_patient_id, user_Id);
        if (hospitalPatient == null)
            throw new InvalidException("Invalid patient.");
        return hospitalPatient;
    }

    public HospitalPatient getPatient(Long patientId, Long hospital_Id) throws InvalidException {
        HospitalPatient patient = hospitalPatientRepository.findByIdAndHospital_Id(patientId, hospital_Id);
        if (patient == null)
            throw new InvalidException("Invalid patient.");
        return patient;
    }

    public List<HospitalPatient> getHospitalPatients(Long userId) throws InvalidException {
        List<HospitalPatient> list = hospitalPatientRepository.findByUser_Id(userId);
        if (list.isEmpty())
            throw new InvalidException("User is not registered with any hospital.");
        return list;
    }

    public boolean isRegisteredWithHospital(Long userId, Long hospital_id) throws InvalidException {
        List<HospitalPatient> patients = hospitalPatientRepository.findByUser_IdAndHospital_Id(userId, hospital_id);
        if (patients.isEmpty())
            throw new InvalidException("You aren't registered with this hospital.");
        return true;
    }
}
