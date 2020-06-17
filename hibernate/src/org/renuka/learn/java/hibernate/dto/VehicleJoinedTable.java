package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;



@Entity
@Inheritance(strategy=InheritanceType.JOINED)

public class VehicleJoinedTable {
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
	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", vehicleName=" + vehicleName + "]";
	}
	public VehicleJoinedTable(String vehicleName) {
		super();		
		this.vehicleName = vehicleName;
	}
	public VehicleJoinedTable() {
		super();
	}
	
	
	
}
