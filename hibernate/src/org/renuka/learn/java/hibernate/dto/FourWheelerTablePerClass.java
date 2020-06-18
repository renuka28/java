package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;

@Entity
public class FourWheelerTablePerClass extends VehicleTablePerClass {

	private String steeringWheel;

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}
	
	public FourWheelerTablePerClass(String vehicleName, String steeringWheel) {
		super(vehicleName);
		this.steeringWheel = steeringWheel;
	}

	@Override
	public String toString() {
		return "VehicleTablePerClass [steeringWheel=" + steeringWheel + "]";
	}
	
}
