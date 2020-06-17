package org.renuka.learn.java.hibernate.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class FourWheelerJoinedTable extends VehicleTablePerClass {

	private String steeringWheel;

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}
	
	public FourWheelerJoinedTable(String vehicleName, String steeringWheel) {
		super(vehicleName);
		this.steeringWheel = steeringWheel;
	}

	@Override
	public String toString() {
		return "VehicleTablePerClass [steeringWheel=" + steeringWheel + "]";
	}
	
}
