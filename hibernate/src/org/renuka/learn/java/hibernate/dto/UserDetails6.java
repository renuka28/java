package org.renuka.learn.java.hibernate.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
@Table(name = "USER_DETAILS6")
public class UserDetails6 {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	private String userName;
	@ElementCollection
	@JoinTable(name="USER_ADDRESS", joinColumns=@JoinColumn(name="USER_ID"))
	
	//changing from set so that we can have index in the table
	//CollectionId and @GenericGenerator are from hibernate and not from JPA
	@GenericGenerator(name="sequencegen", strategy="sequence")
	@CollectionId(columns= {@Column(name="ADDRESS_ID")}, generator = "sequencegen", type = @Type(type="long"))
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
	
	public UserDetails6() {super();}
	public UserDetails6(int userId, String userName, Set<Address2> listofAddress) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.listofAddress = listofAddress;
	}
	@Override
	public String toString() {
		return "UserDetails6 [userId=" + userId + ", userName=" + userName + ", listofAddress=" + listofAddress.toArray().toString() + "]";
	}

	
	
	

}
