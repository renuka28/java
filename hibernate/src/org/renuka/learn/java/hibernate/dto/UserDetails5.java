package org.renuka.learn.java.hibernate.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "USER_DETAILS5")
public class UserDetails5 {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;
	@ElementCollection
	private Set<Address2> listofAddress = new HashSet();
	
	
	
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
	
	public UserDetails5() {super();}
	public UserDetails5(int userId, String userName, Set<Address2> listofAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.listofAddress = listofAddress;
	}
	@Override
	public String toString() {
		return "UserDetails5 [userId=" + userId + ", userName=" + userName + ", listofAddress=" + listofAddress + "]";
	}
	public Set<Address2> getListofAddress() {
		return listofAddress;
	}
	public void setListofAddress(Set<Address2> listofAddress) {
		this.listofAddress = listofAddress;
	}
	
	
	

}
