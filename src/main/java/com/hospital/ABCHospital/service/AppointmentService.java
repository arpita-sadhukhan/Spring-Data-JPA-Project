package com.hospital.ABCHospital.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.Utility.CommonUtility;
import com.hospital.ABCHospital.entity.Appointment;
import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidUserException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.repository.AppointmentRepo;
import com.hospital.ABCHospital.repository.DoctorRepo;
import com.hospital.ABCHospital.repository.PatientRepo;

@Service
public class AppointmentService implements IAppointmentService{

	@Autowired
	AppointmentRepo repo;
	
	@Autowired
	DoctorRepo docRepo;
	
	@Autowired
	PatientRepo patientRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public AppointmentDTO createAppointment(AppointmentDTO appDto) throws InvalidUserException {

		if(appDto.getPatientId() > 0 && appDto.getDoctorId() > 0) {
			Optional<List<Appointment>> appointmentHistory = repo.findByPatientIdAndDoctorId(appDto.getPatientId(), appDto.getDoctorId());
			
			if(appointmentHistory.isPresent()) {
				
				Long noOfPastAppointmentsInPast5Days = checkPastAppointment(appDto, appointmentHistory);
				
				if(noOfPastAppointmentsInPast5Days > 0) {
					throw new DuplicateRecordException(ExceptionStatus.DUPLICATE_APPOINTMENT.getStatusMessage());
				}
			}else {
				return modelMapper.map(createNewAppointment(appDto), AppointmentDTO.class);
			}
		}else {
			throw new IllegalArgumentException(ExceptionStatus.INCOMPLETE_APPOINTMENT.getStatusMessage());
		}
		return null;
	}

	private Long checkPastAppointment(AppointmentDTO appDto, Optional<List<Appointment>> appointmentHistory) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		LocalDate lDate = LocalDate.parse(appDto.getDate());
		
		Long noOfPastAppointments = appointmentHistory.get()
						  .stream()
						  .map(appointment -> appointment.getDateOfAppointment().toLocalDate())
						  .filter(date -> date.isAfter(lDate.minusDays(5)))
						  .collect(Collectors.counting());
		return noOfPastAppointments;
	}

	private Appointment createNewAppointment(AppointmentDTO appDto) throws InvalidUserException {

		Appointment appointment = map(appDto);
		appointment.setStarted(false);
		appointment.setEnded(false);
		
		return repo.save(appointment);
	}

	private Appointment map(AppointmentDTO appDto) throws InvalidUserException {
		
		Appointment appointment = new Appointment();

		Optional<Doctor> doctor = docRepo.findById(appDto.getDoctorId());
		doctor.orElseThrow(() -> new InvalidUserException(ExceptionStatus.DOCTOR_UNREGISTERED.getStatusMessage()));
		
		appointment.setDoctor(doctor.get());
		
		Optional<Patient> patient = patientRepo.findById(appDto.getPatientId());
		patient.orElseThrow(() -> new InvalidUserException(ExceptionStatus.PATIENT_UNREGISTERED.getStatusMessage()));
		
		appointment.setPatient(patient.get());
		String time = appDto.getTime();
		appointment.setAppointmentTime(Time.valueOf(time));
		appointment.setDateOfAppointment(Date.valueOf(appDto.getDate()));
		appointment.setReason(appDto.getReason());
		
		return appointment;
	}

}
