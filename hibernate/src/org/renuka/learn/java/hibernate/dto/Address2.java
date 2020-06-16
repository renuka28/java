package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Embeddable;

@Embeddable
public class Address2 {
	

	private String street;
	private String city;
	private String state;
	private String pincode;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Address2(String street, String city, String state, String pincode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}
	public Address2() {super();}
	
	@Override
	public String toString() {
		return "Address2 [street=" + street + ", city=" + city + ", state=" + state + ", pincode=" + pincode
				+ ", getStreet()=" + getStreet() + ", getCity()=" + getCity() + ", getState()=" + getState()
				+ ", getPincode()=" + getPincode() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}

