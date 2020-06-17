package org.renuka.learn.java.hibernate.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Bike")
public class TwoWheelerSingleTable extends VehicleSingleTable {
	
	private String steeringHandle;

	public String getSteeringHandle() {
		return steeringHandle;
	}

	public void setSteeringHandle(String steeringHandle) {
		this.steeringHandle = steeringHandle;
	}

	
	public TwoWheelerSingleTable(String vehicleName, String steeringHandle) {
		super(vehicleName);
		this.steeringHandle = steeringHandle;
	}

	@Override
	public String toString() {
		return "TwoWheelerSingleTable [steeringHandle=" + steeringHandle + ", vehicleId=" + vehicleId + "]";
	}
	

}
