package com.hospital.ABCHospital.service;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;

public interface IRegistrationService {

	DoctorDTO registerDoctor(DoctorDTO doctorDto);

	PatientDTO registerPatient(PatientDTO patientDto);

}
