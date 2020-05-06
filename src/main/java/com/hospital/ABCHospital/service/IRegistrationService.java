package com.hospital.ABCHospital.service;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidUserException;

public interface IRegistrationService {

	DoctorDTO registerDoctor(DoctorDTO doctorDto) throws DuplicateRecordException;

	PatientDTO registerPatient(PatientDTO patientDto) throws DuplicateRecordException;

}
