package org.renuka.learn.java.hibernate.dto;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "USER_DETAILS3")
public class UserDetails3 {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;	
	@Embedded
	private Address Address;
	
	
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
	public Address getAddress() {
		return Address;
	}
	public void setAddress(Address address) {
		Address = address;
	}
	public UserDetails3(String userName, Address address) {
		super();
		this.userId = userId;
		this.userName = userName;
		Address = address;
	}
	@Override
	public String toString() {
		return "UserDetails3 [userId=" + userId + ", userName=" + userName + ", Address=" + Address + "]";
	}
	
	
	

}
