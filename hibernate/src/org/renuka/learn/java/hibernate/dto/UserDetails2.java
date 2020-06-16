package org.renuka.learn.java.hibernate.dto;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "USER_DETAILS2")
public class UserDetails2 {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;
	private Date joinedDate;
	private String Address;
	private String description;
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
	public Date getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public UserDetails2(String userName, Date joinedDate, String address, String description) {
		super();
	
		this.userName = userName;
		this.joinedDate = joinedDate;
		Address = address;
		this.description = description;
	}
	public UserDetails2(int userId, String userName, Date joinedDate, String address, String description) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.joinedDate = joinedDate;
		Address = address;
		this.description = description;
	}
	
	public UserDetails2() {super();}
	@Override
	public String toString() {
		return "UserDetails2 [userId=" + userId + ", userName=" + userName + ", joinedDate=" + joinedDate + ", Address="
				+ Address + ", description=" + description + "]";
	}
	
	

}
