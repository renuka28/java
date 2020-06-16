package org.renuka.learn.java.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

//all hibernate provided facilities
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "USER_DETAILS7")
public class UserDetails7 {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="USER_ADDRESS2", joinColumns=@JoinColumn(name="USER_ID"))
	private Collection<Address2> listofAddress = new ArrayList();
	
	
	
	public Collection<Address2> getListofAddress() {
		return listofAddress;
	}
	public void setListofAddress(Collection<Address2> listofAddress) {
		this.listofAddress = listofAddress;
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
	
	public UserDetails7() {super();}
	public UserDetails7(int userId, String userName, Set<Address2> listofAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.listofAddress = listofAddress;
	}
	@Override
	public String toString() {
		return "UserDetails7 [userId=" + userId + ", userName=" + userName + ", listofAddress=" + listofAddress.toArray().toString() + "]";
	}
}
