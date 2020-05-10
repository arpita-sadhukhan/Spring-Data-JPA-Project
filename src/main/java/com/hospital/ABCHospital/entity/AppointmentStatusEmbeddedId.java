package com.hospital.ABCHospital.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class AppointmentStatusEmbeddedId implements Serializable{

	private static final long serialVersionUID = -3325061249984090935L;

	private int doctor_id;
	private Date date;

	public AppointmentStatusEmbeddedId() {
		super();
	}

	public AppointmentStatusEmbeddedId(int doctor_id, Date date) {
		super();
		this.doctor_id = doctor_id;
		this.date = date;
	}

	public int getDoctor() {
		return doctor_id;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, doctor_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AppointmentStatusEmbeddedId)) {
			return false;
		}
		AppointmentStatusEmbeddedId other = (AppointmentStatusEmbeddedId) obj;
		return Objects.equals(date, other.date) && Objects.equals(doctor_id, other.doctor_id);
	}
	

}
