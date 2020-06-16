package org.renuka.learn.java.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class VehicleManyToMany {
	
	public VehicleManyToMany(String vehicleName) {
		super();		
		this.vehicleName = vehicleName;
	}


	@Id @GeneratedValue
	public int vehicleId;
	private String vehicleName;
	
	//this allows us to have a many to one so that we can get the user associated with the given vehicle
	@ManyToMany(mappedBy="vehicles")
	private Collection<UserDetailsManyToMany> userList = new ArrayList<UserDetailsManyToMany>();
	
	
	public Collection<UserDetailsManyToMany> getUserList() {
		return userList;
	}
	public void setUserList(Collection<UserDetailsManyToMany> userList) {
		this.userList = userList;
	}
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
	
	
	public VehicleManyToMany() {
		super();
	}
	
	public UserDetailsManyToMany getUserAt(int id) {
		return (UserDetailsManyToMany) userList.toArray()[id];
	}
//	@Override
//	public String toString() {
//		return "VehicleManyToMany [vehicleId=" + vehicleId + ", vehicleName=" + vehicleName + ", userList=" + userList
//				+ "]";
//	}
	
	
	
}
