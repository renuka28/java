package org.renuka.learn.java.hibernate.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Cars_Trucks")
public class FourWheeler extends Vehicle2 {



	private String steeringWheel;

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}
	
	public FourWheeler(String vehicleName, String steeringWheel) {
		super(vehicleName);
		this.steeringWheel = steeringWheel;
	}

	@Override
	public String toString() {
		return "FourWheeler [steeringWheel=" + steeringWheel + "]";
	}
	
}
