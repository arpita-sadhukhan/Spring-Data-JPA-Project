package com.hospital.ABCHospital.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.repository.DoctorRepo;

@Service	
public class RegistrationService implements IRegistrationService{

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	DoctorRepo docRepo;
	
	@Override
	public DoctorDTO registerDoctor(DoctorDTO doctorDto) {
		
		Doctor docEntity = modelMapper.map(doctorDto, Doctor.class);
		Doctor savedDoctor = docRepo.save(docEntity);
		return modelMapper.map(savedDoctor, DoctorDTO.class);
		
	}

}
