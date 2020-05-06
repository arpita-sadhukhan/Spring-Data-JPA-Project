package com.hospital.ABCHospital.service;

import com.hospital.ABCHospital.Dto.AppointmentDTO;
import com.hospital.ABCHospital.exception.DuplicateRecordException;
import com.hospital.ABCHospital.exception.InvalidDataException;
import com.hospital.ABCHospital.exception.InvalidUserException;

public interface IAppointmentService {

	AppointmentDTO createAppointment(AppointmentDTO appDto) throws InvalidUserException, InvalidDataException, DuplicateRecordException;

}
