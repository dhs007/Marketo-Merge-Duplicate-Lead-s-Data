package com.tadigital.entity;

public class Leads {

	private String ID;
	private String FirstName;
	private String LastName;
	private String Country;
	private String Email;
	private String sfdc;

	public Leads() {

	}

	public String getSFDC() {
		return this.sfdc;
	}

	public void setSFDC(String sfdc) {
		this.sfdc = sfdc;
	}

	public String getID() {
		return this.ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	public String getFirstName() {
		return this.FirstName;
	}

	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	public String getLastName() {
		return this.LastName;
	}

	public void setLastName(String lastName) {
		this.LastName = lastName;
	}

	public String getCountry() {
		return this.Country;
	}

	public void setCountry(String country) {
		this.Country = country;
	}

	public String getEmail() {
		return this.Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

}
