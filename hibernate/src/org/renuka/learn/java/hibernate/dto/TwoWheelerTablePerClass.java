package org.renuka.learn.java.hibernate.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class TwoWheelerTablePerClass extends VehicleTablePerClass {
	
	private String steeringHandle;

	public String getSteeringHandle() {
		return steeringHandle;
	}

	public void setSteeringHandle(String steeringHandle) {
		this.steeringHandle = steeringHandle;
	}

	
	public TwoWheelerTablePerClass(String vehicleName, String steeringHandle) {
		super(vehicleName);
		this.steeringHandle = steeringHandle;
	}

	@Override
	public String toString() {
		return "VehicleTablePerClass [steeringHandle=" + steeringHandle + ", vehicleId=" + vehicleId + "]";
	}
	

}
