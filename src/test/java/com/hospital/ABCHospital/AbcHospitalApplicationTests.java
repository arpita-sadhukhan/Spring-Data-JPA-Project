package com.hospital.ABCHospital;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.entity.Insurance;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.repository.DoctorRepo;
import com.hospital.ABCHospital.repository.PatientRepo;

@SpringBootTest
class AbcHospitalApplicationTests {

	@Autowired
	DoctorRepo docRepo;
	
	@Autowired
	PatientRepo patientRepo;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void createDoctor() {
		Doctor doctor = new Doctor();
		doctor.setFirstName("Niras");
		doctor.setLastName("Gupta");
		doctor.setSpeciality("Cardiologist");
		
		docRepo.save(doctor);
	}

	@Test
	public void getDoctorById() {
		Optional<Doctor> doctor = docRepo.findById(1);
		if(doctor.isPresent()) {
			System.out.println(doctor.get().getFirstName() + " " + doctor.get().getLastName());
		}
		else {
			System.out.println("No records found");
		}
	}
	
	@Test
	public void createPatient() {
		Patient patient = new Patient();
		patient.setFirstName("Arpita");
		patient.setLastName("Gupta");
		patient.setPhone("9876543210");
		
		Insurance insurance = new Insurance();
		insurance.setName("New India");
		insurance.setCoPay(100.00);
		patient.setProviderName(insurance);
		
		patientRepo.save(patient);
		
		Optional<Patient> savedPatient = patientRepo.findByFirstName("Arpita");
		savedPatient.ifPresent(pat -> System.out.println(pat.getFirstName() + " " + pat.getLastName()));
	}
}
