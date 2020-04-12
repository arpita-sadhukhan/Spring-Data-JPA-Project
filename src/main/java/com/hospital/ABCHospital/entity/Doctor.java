package com.hospital.ABCHospital.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String speciality;

	@OneToMany(mappedBy = "doctor")
	private Set<Appointment> appointments;

	@ManyToMany(mappedBy = "doctors")
	private Set<Patient> patients;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void addAppointment(Appointment appointment) {
		if (this.appointments == null) {
			this.appointments = new HashSet<Appointment>();
		}
		this.appointments.add(appointment);
		appointment.setDoctor(this);
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", speciality=" + speciality
				+ ", appointments=" + appointments + ", patients=" + patients + "]";
	}

}
