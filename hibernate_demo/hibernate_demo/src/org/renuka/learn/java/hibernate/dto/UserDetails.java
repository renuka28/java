package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {
	
	public UserDetails(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}
	

	private int userId;
	

	private String userName;
	
	//we can have the annotation on the getter which will help us to modify the value if required
	@Id
	@Column(name = "USER_ID")
	public int getUserId() {
		return userId;
	}	
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName + " updated using getter";
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	

}
