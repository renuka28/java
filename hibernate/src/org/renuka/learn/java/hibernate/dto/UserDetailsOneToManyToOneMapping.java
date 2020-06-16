package org.renuka.learn.java.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "USER_DETAILS_ONE_TO_MANY")
public class UserDetailsOneToManyToOneMapping {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;
	
	//if this was a value type we can use @Embeddable and @Embedded tags
	//added optional tags to customize the table created for joining both the entities. 
	//if nothing specified it will create it as entity1_entity2 for eg USER_DETAILS_ONE_TO_MANY_Vehicle
	@OneToMany
	@JoinTable(name="USER_VEHICLE", 
		joinColumns=@JoinColumn(name="USER_ID"), 
		inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))
	private Collection<VehicleOneToMany> vehicles = new ArrayList<VehicleOneToMany>();
	
	
	public Collection<VehicleOneToMany> getVehicles() {
		return vehicles;
	}
	public void setVehicles(Collection<VehicleOneToMany> vehicles) {
		this.vehicles = vehicles;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "UserDetailsOneToManyToOneMapping [userId=" + userId + ", userName=" + userName + ", vehicles=" + vehicles
				+ "]";
	}

	public VehicleOneToMany getVehicleAt(int id) {
		return (VehicleOneToMany) vehicles.toArray()[id];
	}

}
