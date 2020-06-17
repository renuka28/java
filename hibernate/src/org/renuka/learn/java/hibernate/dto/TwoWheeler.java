package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;

@Entity
public class TwoWheeler extends Vehicle2 {
	
	private String steeringHandle;

	public String getSteeringHandle() {
		return steeringHandle;
	}

	public void setSteeringHandle(String steeringHandle) {
		this.steeringHandle = steeringHandle;
	}

	
	public TwoWheeler(String vehicleName, String steeringHandle) {
		super(vehicleName);
		this.steeringHandle = steeringHandle;
	}

	@Override
	public String toString() {
		return "TwoWheeler [steeringHandle=" + steeringHandle + ", vehicleId=" + vehicleId + "]";
	}
	

}
