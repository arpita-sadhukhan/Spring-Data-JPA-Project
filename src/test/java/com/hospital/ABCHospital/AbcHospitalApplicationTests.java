package com.hospital.ABCHospital;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.ABCHospital.entity.Appointment;
import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.entity.Insurance;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.repository.AppointmentRepo;
import com.hospital.ABCHospital.repository.DoctorRepo;
import com.hospital.ABCHospital.repository.PatientRepo;

@SpringBootTest
class AbcHospitalApplicationTests {
	/*
	 * 
	 * @Autowired DoctorRepo docRepo;
	 * 
	 * @Autowired PatientRepo patientRepo;
	 * 
	 * @Autowired AppointmentRepo appoRepo;
	 * 
	 * @Test void contextLoads() { }
	 * 
	 * @Test public void createDoctor() { Doctor doctor = new Doctor();
	 * doctor.setFirstName("Niras"); doctor.setLastName("Gupta");
	 * doctor.setSpeciality("Cardiologist");
	 * 
	 * docRepo.save(doctor); }
	 * 
	 * @Test public void getDoctorById() { Optional<Doctor> doctor =
	 * docRepo.findById(1); if(doctor.isPresent()) {
	 * System.out.println(doctor.get().getFirstName() + " " +
	 * doctor.get().getLastName()); } else { System.out.println("No records found");
	 * } }
	 * 
	 * @Test public void createPatient() { Patient patient = new Patient();
	 * patient.setFirstName("Arpita"); patient.setLastName("Gupta");
	 * patient.setPhone("9876543210");
	 * 
	 * Insurance insurance = new Insurance(); insurance.setName("New India");
	 * insurance.setCoPay(100.00); patient.setProviderName(insurance);
	 * 
	 * patientRepo.save(patient);
	 * 
	 * Optional<Patient> savedPatient = patientRepo.findByFirstName("Arpita");
	 * savedPatient.ifPresent(pat -> System.out.println(pat.getFirstName() + " " +
	 * pat.getLastName())); }
	 * 
	 * @Test
	 * 
	 * @Transactional public void createAppointment() {
	 * 
	 * Optional<Patient> savedPatient = patientRepo.findByFirstName("Arpita");
	 * Optional<Doctor> docOptional = docRepo.findById(1);
	 * 
	 * if(savedPatient.isPresent() && docOptional.isPresent()) {
	 * 
	 * Appointment appointment = new Appointment();
	 * appointment.setPatient(savedPatient.get());
	 * appointment.setDoctor(docOptional.get());
	 * 
	 * Timestamp time = new Timestamp(new Date().getTime());
	 * appointment.setAppointmentTime(time);
	 * 
	 * appointment.setStarted(true); appointment.setReason("Fever");
	 * 
	 * appoRepo.save(appointment); }
	 * 
	 * Iterable<Appointment> appointments = appoRepo.findAll(); if(appointments !=
	 * null) { for(Appointment app : appointments) {
	 * System.out.println("Appointment set for Patint : " +
	 * app.getPatient().getFirstName()); } } else {
	 * System.out.println("No Appointment records saved"); } }
	 */}
