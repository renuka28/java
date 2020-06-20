package org.renuka.learn.java.hibernate.dto;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("deprecation")
@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "USER_DETAILS_CRUD")
//does the update only if there is somethign to update. Checks by doing select first
@org.hibernate.annotations.Entity(selectBeforeUpdate=true)
@NamedQuery(name="UserDetailsCrud.userById", query="from UserDetailsCrud where userId = :userID")
@NamedQuery(name="UserDetailsCrud.allUsersWithHigerId", query="from UserDetailsCrud where userId > :userID")
@NamedQuery(name="UserDetailsCrud.maxUserId", query="select max(userId) from UserDetailsCrud")
//use standard sql query as you would use in database prompt. refer to table names and column names
@NamedNativeQuery(name="UserDetailsCrud.byName", query = "select * from USER_DETAILS_CRUD where username = :userName", resultClass = UserDetailsCrud.class)
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
	public UserDetailsCrud() {
		super();
	}	
	
	

}
