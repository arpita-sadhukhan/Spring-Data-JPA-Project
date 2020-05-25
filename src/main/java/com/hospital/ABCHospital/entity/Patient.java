package com.hospital.ABCHospital.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String firstName;
	private String lastName;
	private String phone;
	@Embedded
	private Insurance providerName;

	@OneToMany(mappedBy = "patient")
	private Set<Appointment> appointments;

	@ManyToMany
	@JoinTable(name = "patients_doctors", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Doctor> doctors;
	
	@OneToMany(mappedBy = "patient")
	private Set<Payment> payments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Insurance getProviderName() {
		return providerName;
	}

	public void setProviderName(Insurance providerName) {
		this.providerName = providerName;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void addAppointment(Appointment appointment) {
		if (this.appointments == null) {
			this.appointments = new HashSet<Appointment>();
		}
		this.appointments.add(appointment);
		appointment.setPatient(this);
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", providerName=" + providerName + ", appointments=" + appointments + ", doctors=" + doctors + "]";
	}

	public Set<Payment> getPayment() {
		return this.payments;
	}

	public void addPayment(Payment payment) {
		if(this.payments == null) {
			this.payments = new HashSet<Payment>();
		}
		this.payments.add(payment);
		payment.setPatient(this);
	}

}