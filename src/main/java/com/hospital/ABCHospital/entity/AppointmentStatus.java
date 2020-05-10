package com.hospital.ABCHospital.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class AppointmentStatus {

	@EmbeddedId
	private AppointmentStatusEmbeddedId id;

	@Column(columnDefinition = "int default 10")
	private int slotsAvailable;

	public AppointmentStatusEmbeddedId getId() {
		return id;
	}

	public void setId(AppointmentStatusEmbeddedId id) {
		this.id = id;
	}

	public int getSlotsAvailable() {
		return slotsAvailable;
	}

	public void setSlotsAvailable(int slotsAvailable) {
		this.slotsAvailable = slotsAvailable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, slotsAvailable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AppointmentStatus)) {
			return false;
		}
		AppointmentStatus other = (AppointmentStatus) obj;
		return Objects.equals(id, other.id) && slotsAvailable == other.slotsAvailable;
	}

}
