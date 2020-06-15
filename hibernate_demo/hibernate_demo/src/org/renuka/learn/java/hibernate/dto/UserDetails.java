package org.renuka.learn.java.hibernate.dto;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {
	
	private String Address;
	
	//hibernate assumes this to be clob 
	@Lob
	private String description;
	private Date joinedDate;
	@Temporal(TemporalType.TIME)
	private Date joinedDateTemporal;
	
	@Override
	public String toString() {
		return "UserDetails [Address=" + Address + ", description=" + description + ", joinedDate=" + joinedDate
				+ ", joinedDateTemporal=" + joinedDateTemporal + ", userId=" + userId + ", userName=" + userName + "]";
	}

	public Date getJoinedDateTemporal() {
		return joinedDateTemporal;
	}

	public void setJoinedDateTemporal(Date joinedDateTemporal) {
		this.joinedDateTemporal = joinedDateTemporal;
	}
	private int userId;
	//this is basic column and hibernate will use default. Basic is the default
	@Basic
	private String userName;
	
	//these fields won't be saved as they are either static or marked as transient 
	private static String stringStaticWontBeSaved = "stringStaticWontBeSaved";
	@Transient 
	private static String stringTransientWontBeSaved = "stringTransientWontBeSaved";

	public UserDetails()	{}
	
	public UserDetails(int userId, String userName, Date joinedDate, String address, String description) {
		super();
		Address = address;
		this.description = description;
		this.joinedDate = this.joinedDateTemporal = joinedDate;
		this.userId = userId;
		this.userName = userName;
	}
	public UserDetails(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public String getAddress() {
		return Address;
	}

	public String getDescription() {
		return description;
	}


	public Date getJoinedDate() {
		return joinedDate;
	}
	//we can have the annotation on the getter which will help us to modify the value if required
	@Id
	@Column(name = "USER_ID")
	public int getUserId() {
		return userId;
	}
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName + " updated using getter";
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	
	public void setDescription(String description) {
		this.description = description;
	}	
	
	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	

}
