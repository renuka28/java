package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_DETAILS_CRUD")
public class UserDetailsCrud {
	@Id
	private int userId;
	private String userName;
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
		return "UserDetailsCrud [userId=" + userId + ", userName=" + userName + "]";
	}
	public UserDetailsCrud(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}	

}
