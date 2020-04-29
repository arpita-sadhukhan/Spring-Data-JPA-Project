package com.hospital.ABCHospital.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.DoctorDTO;
import com.hospital.ABCHospital.Dto.PatientDTO;
import com.hospital.ABCHospital.entity.Appointment;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.repository.AppointmentRepo;

@Service
public class AppointmentService implements IAppointmentService{

	@Autowired
	AppointmentRepo repo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public AppointmentDTO createAppointment(AppointmentDTO appDto) {

		PatientDTO patientDto = appDto.getPatientDto();
		DoctorDTO doctorDto = appDto.getDoctorDto();
		
		if(patientDto != null && doctorDto != null) {
			Optional<List<Appointment>> appointmentHistory = repo.findByPatientIdAndDoctorId(patientDto.getId(), doctorDto.getId());
			
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
		LocalDate lDate = LocalDate.parse(appDto.getTime(),format);
		
		Long noOfPastAppointments = appointmentHistory.get()
						  .stream()
						  .map(appointment -> appointment.getAppointmentTime().toLocalDateTime())
						  .filter(date -> date.isAfter(lDate.minusDays(5).atStartOfDay()))
						  .collect(Collectors.counting());
		return noOfPastAppointments;
	}

	private Appointment createNewAppointment(AppointmentDTO appDto) {

		Appointment appointment = modelMapper.map(appDto, Appointment.class);
		appointment.setStarted(false);
		appointment.setEnded(false);
		
		return repo.save(appointment);
	}

}
