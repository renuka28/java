package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class VehicleOneToMany {
	@Id @GeneratedValue
	public int vehicleId;
	private String vehicleName;
	
	//this allows us to have a many to one so that we can get the user associated with the given vehicle
	@ManyToOne
	private UserDetailsOneToManyToOneMapping user;
	
	
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public UserDetailsOneToManyToOneMapping getUser() {
		return user;
	}
	public void setUser(UserDetailsOneToManyToOneMapping user) {
		this.user = user;
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
	public VehicleOneToMany(String vehicleName, UserDetailsOneToManyToOneMapping user) {
		super();		
		this.vehicleName = vehicleName;
		this.user = user;
	}
	public VehicleOneToMany() {
		super();
	}
	
	
	
}
