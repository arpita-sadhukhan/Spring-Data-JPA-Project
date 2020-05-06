package com.hospital.ABCHospital.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AppointmentStatus {

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	private Date date;
	private int slotsAvailable;

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSlotsAvailable() {
		return slotsAvailable;
	}

	public void setSlotsAvailable(int slotsAvailable) {
		this.slotsAvailable = slotsAvailable;
	}

}
