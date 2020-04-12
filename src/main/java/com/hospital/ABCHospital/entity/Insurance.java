package com.hospital.ABCHospital.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Insurance {

	private String name;
	private double coPay;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCoPay() {
		return coPay;
	}

	public void setCoPay(double coPay) {
		this.coPay = coPay;
	}

}
