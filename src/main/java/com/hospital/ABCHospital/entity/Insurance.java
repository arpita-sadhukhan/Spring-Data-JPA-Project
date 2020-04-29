package com.hospital.ABCHospital.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Insurance {

	private String provider;
	private double coPay;

	public double getCoPay() {
		return coPay;
	}

	public void setCoPay(double coPay) {
		this.coPay = coPay;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
