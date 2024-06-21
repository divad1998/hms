package com.ingryd.hms.service;

import com.ingryd.hms.entity.HospitalPatient;
import com.ingryd.hms.exception.InvalidException;
import com.ingryd.hms.repository.HospitalPatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<HospitalPatient> getAllHospitalPatient (Long hospital_id) {
        List<HospitalPatient> patientList = hospitalPatientRepository.findAll();

        return patientList
                .stream()
                .filter(list -> list.getUser().isEnabled())
                .filter(list -> Objects.equals(list.getHospital().getId(), hospital_id))
                .collect(Collectors.toList());
    }
}
