package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.renuka.learn.java.hibernate.dto.Vehicle;


@Entity
@Table(name = "USER_DETAILS_ONE_TO_ONE")
public class UserDetailsOneToOneMapping {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;
	
	//if this was a value type we can use @Embeddable and @Embedded tags
	@OneToOne
	@JoinColumn(name="VEHICLE_ID")
	private Vehicle vehicle;
	
	
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
	
	public UserDetailsOneToOneMapping() {super();}
	
	public UserDetailsOneToOneMapping(String userName) {
		super();
		this.userName = userName;
	}
	
	public UserDetailsOneToOneMapping(String userName, Vehicle vehicle) {
		super();
		
		this.userName = userName;
		this.vehicle = vehicle;
		
	}
	
	

	@Override
	public String toString() {
		return "UserDetailsOneToOneMapping [userId=" + userId + ", userName=" + userName + ", vehicle=" + vehicle + "]";
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
