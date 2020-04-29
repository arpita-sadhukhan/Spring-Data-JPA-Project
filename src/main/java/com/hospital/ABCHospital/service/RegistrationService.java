package com.hospital.ABCHospital.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.InsuranceDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.entity.Insurance;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.repository.DoctorRepo;
import com.hospital.ABCHospital.repository.PatientRepo;

@Service	
public class RegistrationService implements IRegistrationService{

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	DoctorRepo docRepo;
	
	@Autowired
	PatientRepo patientRepo;
	
	@Override
	public DoctorDTO registerDoctor(DoctorDTO doctorDto) {

		Doctor docEntity = modelMapper.map(doctorDto, Doctor.class);

		if (!checkExistingDoctor(docEntity)) {
			Doctor savedDoctor = docRepo.save(docEntity);
			return modelMapper.map(savedDoctor, DoctorDTO.class);
		} else
			throw new DuplicateRecordException(ExceptionStatus.DUPLICATE_DOCTOR.getStatusMessage());

	}

	private boolean checkExistingDoctor(Doctor docEntity) {
		Optional<Doctor> existingDoctor = docRepo.findByFirstNameAndLastName(docEntity.getFirstName(), docEntity.getLastName());
		return existingDoctor.isPresent();
	}

	@Override
	public PatientDTO registerPatient(PatientDTO patientDto) {

		Patient patient = modelMapper.map(patientDto, Patient.class);
		Insurance insurance = modelMapper.map(patientDto.getInsurance(), Insurance.class);
		patient.setProviderName(insurance);

		if (patient != null && !checkExistingPatient(patient)) {
			Patient newPatient = patientRepo.save(patient);
			PatientDTO newPatientDto = modelMapper.map(newPatient, PatientDTO.class);
			InsuranceDTO insDto = modelMapper.map(newPatient.getProviderName(), InsuranceDTO.class);
			newPatientDto.setInsurance(insDto);
			return newPatientDto;
		} else
			throw new DuplicateRecordException(ExceptionStatus.DUPLICATE_PATIENT.getStatusMessage());
	}

	private boolean checkExistingPatient(Patient patient) {
		Optional<Patient> existingPatient = patientRepo.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName());
		return existingPatient.isPresent();
	}

}
