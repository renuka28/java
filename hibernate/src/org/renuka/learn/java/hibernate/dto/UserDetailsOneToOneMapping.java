package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "USER_DETAILS_ONE_TO_ONE")
public class UserDetailsOneToOneMapping {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
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
	
	public UserDetailsOneToOneMapping() {super();}
	public UserDetailsOneToOneMapping(int userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
		
	}
	@Override
	public String toString() {
		return "UserDetails7 [userId=" + userId + ", userName=" + userName + "]";
	}
}
