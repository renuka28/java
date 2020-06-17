package org.renuka.learn.java.hibernate.dto;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;



@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn allows us to configure the disrimintor (the column in db which identifies which type of object the row belongs to)

@DiscriminatorColumn(
		name="VEHICLE_TYPE",
		discriminatorType=DiscriminatorType.STRING
		)
public class Vehicle2 {
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
	public Vehicle2(String vehicleName) {
		super();		
		this.vehicleName = vehicleName;
	}
	public Vehicle2() {
		super();
	}
	
	
	
}
