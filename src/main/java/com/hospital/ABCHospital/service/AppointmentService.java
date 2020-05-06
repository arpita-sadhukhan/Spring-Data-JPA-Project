package com.hospital.ABCHospital.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.entity.Appointment;
import com.hospital.ABCHospital.entity.AppointmentStatus;
import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidDataException;
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
	
	private final String TO_CREATE = "TO_CREATE";
	private final String TO_UPDATE = "TO_UPDATE";
	private final String DUPLICATE = "DUPLICATE";
	
	@Transactional
	@Override
	public AppointmentDTO createAppointment(AppointmentDTO appDto) throws InvalidUserException, InvalidDataException, DuplicateRecordException {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		LocalDate dateOfAppointment = LocalDate.parse(appDto.getDate());
		if(dateOfAppointment.isBefore(LocalDate.now())) {
			throw new InvalidDataException(ExceptionStatus.BACK_DATE.getStatusMessage());
		}
		if(appDto.getPatientId() > 0 && appDto.getDoctorId() > 0) {
			Optional<Appointment> appointmentHistory = repo.findByPatientIdAndDoctorId(appDto.getPatientId(), appDto.getDoctorId());
			
			if(appointmentHistory.isPresent()) {
				
				Appointment app = appointmentHistory.get();
				String appointmentStatus = checkAppointmentStatus(appDto, app);
				
				if(appointmentStatus.equals(DUPLICATE)) {
					throw new DuplicateRecordException(ExceptionStatus.DUPLICATE_APPOINTMENT.getStatusMessage());
				}else if(appointmentStatus.equals(TO_UPDATE)) {
					app.setDateOfAppointment(Date.valueOf(appDto.getDate()));
					Appointment updatedAppointment = repo.save(app);
					return modelMapper.map(updatedAppointment, AppointmentDTO.class);
				}
			}else {
				return modelMapper.map(createNewAppointment(appDto), AppointmentDTO.class);
			}
		}else {
			throw new IllegalArgumentException(ExceptionStatus.INCOMPLETE_APPOINTMENT.getStatusMessage());
		}
		return null;
	}

	private String checkAppointmentStatus(AppointmentDTO appDto, Appointment appointmentHistory) {
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		LocalDate currentAppointmentDate = LocalDate.parse(appDto.getDate());
		LocalDate lastAppointmentDate = appointmentHistory.getDateOfAppointment().toLocalDate();
		
		if(lastAppointmentDate.isBefore(LocalDate.now()))
			return TO_CREATE;
		else if(lastAppointmentDate.isAfter(currentAppointmentDate)) {
			//TODO check availability slots of appointment for the doctor
			
			return TO_UPDATE;
		}else return DUPLICATE;
		
		/*Long noOfPastAppointments = appointmentHistory.get()
						  .stream()
						  .map(appointment -> appointment.getDateOfAppointment().toLocalDate())
						  .filter(date -> date.isAfter(lDate.minusDays(5)))
						  .collect(Collectors.counting());*/
	}

	private Appointment createNewAppointment(AppointmentDTO appDto) throws InvalidUserException {

		Optional<Patient> patient = patientRepo.findById(appDto.getPatientId());
		patient.orElseThrow(() -> new InvalidUserException(ExceptionStatus.PATIENT_UNREGISTERED.getStatusMessage()));
		
		Appointment appointment = map(appDto);
		appointment.setStarted(false);
		appointment.setEnded(false);
		
		Appointment newAppointment = repo.save(appointment);
		updateAppointmentStatus(newAppointment);
		return newAppointment;
	}

	private void updateAppointmentStatus(Appointment newAppointment) {

		
		AppointmentStatus appStatus = new AppointmentStatus();
		appStatus.setDoctor(newAppointment.getDoctor());
		//appStatus
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
