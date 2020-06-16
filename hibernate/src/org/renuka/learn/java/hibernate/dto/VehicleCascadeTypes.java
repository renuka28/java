package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class VehicleCascadeTypes {
	
	
	public VehicleCascadeTypes(String vehicleName) {
		super();
		this.vehicleName = vehicleName;
	}

	@Id @GeneratedValue
	public int vehicleId;
	private String vehicleName;
	
	
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	
	public VehicleCascadeTypes() {
		super();
	}
	
	
	
}
