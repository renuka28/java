package org.renuka.learn.java.hibernate.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Cars_Trucks")
public class FourWheelerSingleTable extends VehicleSingleTable {

	private String steeringWheel;

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}
	
	public FourWheelerSingleTable(String vehicleName, String steeringWheel) {
		super(vehicleName);
		this.steeringWheel = steeringWheel;
	}

	@Override
	public String toString() {
		return "FourWheeler [steeringWheel=" + steeringWheel + "]";
	}
	
}
