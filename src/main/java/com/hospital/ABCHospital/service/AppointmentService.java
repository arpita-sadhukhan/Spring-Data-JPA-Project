package com.hospital.ABCHospital.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.Dto.PaymentDetails;
import com.hospital.ABCHospital.entity.Appointment;
import com.hospital.ABCHospital.entity.AppointmentStatus;
import com.hospital.ABCHospital.entity.AppointmentStatusEmbeddedId;
import com.hospital.ABCHospital.entity.Doctor;
import com.hospital.ABCHospital.entity.Patient;
import com.hospital.ABCHospital.entity.Payment;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;
import com.hospital.ABCHospital.exceptionHandler.ExceptionStatus;
import com.hospital.ABCHospital.repository.AppointmentRepo;
import com.hospital.ABCHospital.repository.AppointmentStatusRepo;
import com.hospital.ABCHospital.repository.DoctorRepo;
import com.hospital.ABCHospital.repository.PatientRepo;
import com.hospital.ABCHospital.repository.PaymentRepo;

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
	
	@Autowired
	AppointmentStatusRepo appRepo;
	
	@Autowired
	PaymentRepo paymentRepo;
	
	private final String TO_CREATE = "TO_CREATE";
	private final String TO_UPDATE = "TO_UPDATE";
	private final String DUPLICATE = "DUPLICATE";
	
	@Transactional
	@Override
	public AppointmentDTO createAppointment(AppointmentDTO appDto) throws InvalidUserException, InvalidDataException, DuplicateRecordException {

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
					
					int slotsAvailable = checkAppointmentSlots(app);
					if (slotsAvailable > 1) {
						app.setDateOfAppointment(Date.valueOf(appDto.getDate()));
						Appointment updatedAppointment = repo.save(app);
						return modelMapper.map(updatedAppointment, AppointmentDTO.class);
					}else {
						throw new InvalidDataException(ExceptionStatus.INCOMPLETE_APPOINTMENT.getStatusMessage());
					}
				}
			}else {
				return modelMapper.map(createNewAppointment(appDto), AppointmentDTO.class);
			}
		}else {
			throw new IllegalArgumentException(ExceptionStatus.INCOMPLETE_APPOINTMENT.getStatusMessage());
		}
		return null;
	}

	private int checkAppointmentSlots(Appointment appointment) {

		Optional<AppointmentStatus> appStatus = appRepo.findById(new AppointmentStatusEmbeddedId(appointment.getDoctor().getId(), appointment.getDateOfAppointment()));
		if(appStatus.isPresent()) {
			AppointmentStatus status = appStatus.get();
			return status.getSlotsAvailable();
		}
		return 0;
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

		
		Optional<AppointmentStatus> appStatus = appRepo.findById(new AppointmentStatusEmbeddedId(newAppointment.getDoctor().getId(), newAppointment.getDateOfAppointment()));
		if(appStatus.isPresent()) {
			AppointmentStatus appointmentStatus = appStatus.get();
			if(appointmentStatus.getSlotsAvailable() > 1) {
				appointmentStatus.setSlotsAvailable(appointmentStatus.getSlotsAvailable() - 1);
				
				appRepo.save(appointmentStatus);
			}
		}else {
			AppointmentStatus status = new AppointmentStatus();
			status.setId(new AppointmentStatusEmbeddedId(newAppointment.getDoctor().getId(), newAppointment.getDateOfAppointment()));
			status.setSlotsAvailable(9);
			
			appRepo.save(status);
		}
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

	@Override
	@Transactional
	public AppointmentDTO checkIn(AppointmentDTO appDto) throws InvalidDataException {

		Appointment appointment = fetchAppointment(appDto.getPatientId(), appDto.getDoctorId(), appDto.getDate());
		
		if(appDto.getPaymentDetails() == null)
			throw new InvalidDataException(ExceptionStatus.PAYMENT_DETAILS_MISSING.getStatusMessage());
		
		Payment processedPayment = processPayment(appDto.getPaymentDetails(), appointment);
		if(processedPayment != null) {
			appointment.setStarted(true);
			Appointment updatedAppointment = repo.save(appointment);
			
			AppointmentDTO appointmentDTO = modelMapper.map(updatedAppointment, AppointmentDTO.class);
			PaymentDetails paymentDetails = modelMapper.map(processedPayment, PaymentDetails.class);
			appointmentDTO.setPaymentDetails(paymentDetails);
			
			return appointmentDTO;
		}
		return null;
	}

	private Payment processPayment(PaymentDetails paymentDetails, Appointment appointment) {

		if(paymentDetails != null) {
			Payment payment = modelMapper.map(paymentDetails, Payment.class);
			payment.setPatient(appointment.getPatient());
			
			if(payment != null) {
				Payment processedPayment = paymentRepo.save(payment);
				return processedPayment;
			}
		}
			
		return null;
	}

	private Appointment fetchAppointment(int patientId, int doctorId, String date) throws InvalidDataException {

		Optional<Appointment> appointment = repo.findByPatientIdAndDoctorIdAndDateOfAppointment(patientId, doctorId,
				Date.valueOf(date));
		appointment.orElseThrow(
				() -> new InvalidDataException(ExceptionStatus.NO_APPOINTMENTS_AVAILABLE.getStatusMessage()));

		return appointment.get();
	}


}
