package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class VehicleManyToMany {
	@Id @GeneratedValue
	public int vehicleId;
	private String vehicleName;
	
	//this allows us to have a many to one so that we can get the user associated with the given vehicle
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private UserDetailsManyToMany user;
	
	
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public UserDetailsManyToMany getUser() {
		return user;
	}
	public void setUser(UserDetailsManyToMany user) {
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
	public VehicleManyToMany(String vehicleName, UserDetailsManyToMany user) {
		super();		
		this.vehicleName = vehicleName;
		this.user = user;
	}
	public VehicleManyToMany() {
		super();
	}
	
	
	
}
