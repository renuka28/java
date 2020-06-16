package org.renuka.learn.java.hibernate.dto;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "USER_DETAILS4")
public class UserDetails4 {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;	
	@Embedded
	@AttributeOverrides(value = {  
		@AttributeOverride(name="street", column=@Column(name="HOME_STREET")),
		@AttributeOverride(name="city", column=@Column(name="HOME_CITY")),
		@AttributeOverride(name="state", column=@Column(name="HOME_STATE")),
		@AttributeOverride(name="pincode", column=@Column(name="HOME_PINCODE"))
	})
	private Address2 homeAddress;
	@Embedded
	@AttributeOverrides(value = {  
			@AttributeOverride(name="street", column=@Column(name="OFFICE_STREET")),
			@AttributeOverride(name="city", column=@Column(name="OFFICE_CITY")),
			@AttributeOverride(name="state", column=@Column(name="OFFICE_STATE")),
			@AttributeOverride(name="pincode", column=@Column(name="OFFICE_PINCODE"))
		})
	private Address2 officeAddress;
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
	public Address2 getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Address2 homeAddress) {
		this.homeAddress = homeAddress;
	}
	public Address2 getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(Address2 officeAddress) {
		this.officeAddress = officeAddress;
	}
	@Override
	public String toString() {
		return "UserDetails4 [userId=" + userId + ", userName=" + userName + ", homeAddress=" + homeAddress
				+ ", officeAddress=" + officeAddress + "]";
	}
	public UserDetails4(String userName, Address2 homeAddress, Address2 officeAddress) {
		super();		
		this.userName = userName;
		this.homeAddress = homeAddress;
		this.officeAddress = officeAddress;
	}
	
	
	public UserDetails4() {
		super();
	}
	
	

}
