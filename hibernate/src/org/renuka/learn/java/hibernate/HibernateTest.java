package org.renuka.learn.java.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.renuka.learn.java.hibernate.dto.Address;
import org.renuka.learn.java.hibernate.dto.Address2;
import org.renuka.learn.java.hibernate.dto.UserDetails;
import org.renuka.learn.java.hibernate.dto.UserDetails2;
import org.renuka.learn.java.hibernate.dto.UserDetails3;
import org.renuka.learn.java.hibernate.dto.UserDetails4;
import org.renuka.learn.java.hibernate.dto.UserDetails5;

public class HibernateTest {

	private static SessionFactory sessionFactory = null;
	private static int currentUserId = 1;
	public static void main(String[] args) {		
		 
		setupSessionFactoryDB();
		demoInsert();
		demoReterive();
		demoPrimaryId();
		demoEmbeddedValueType();
		demoEmbeddedValueType2();
		demoList();

	}
	
	public static void demoList() {
		
		
		System.out.println("inserting user details to user_details5 which stores Address2 using HashSet");		
		Address2 homeAddress = new Address2("dummy home street", "home-tx", "home-la", "home-85151");
		Address2 oldHomeAddress = new Address2("dummy old home street", "old-home-tx", "old-home-la", "old-home-85151");		
		Address2 officeAddress = new Address2("dummy office street", "office-tx", "office-la", "office-85151");
		Address2 oldOOfficeAddress = new Address2("dummy old-office street", "old-office-tx", "old-office-la", "old-office-85151");
		UserDetails5 user5 = new UserDetails5();
		user5.setUserName("User with variable number of addresses");		
		HashSet<Address2> userAddresses = (HashSet<Address2>) user5.getListofAddress();
		
		userAddresses.add(homeAddress);
		userAddresses.add(oldHomeAddress);
		userAddresses.add(officeAddress);
		userAddresses.add(oldOOfficeAddress);
		user5.setListofAddress(userAddresses);
		
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user5);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoEmbeddedValueType2() {
		
		
		System.out.println("inserting user details to user_details4 with embedded value types");		
		Address2 homeAddress = new Address2("dummy home street", "home-tx", "home-la", "home-85151");		
		Address2 officeAddress = new Address2("dummy office street", "office-tx", "office-la", "office-85151");
		UserDetails4 user4 = new UserDetails4("user name", homeAddress, officeAddress);
		System.out.println(user4.toString());
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user4);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoEmbeddedValueType() {
		
		
		System.out.println("inserting user details to user_details3 with embedded value types");		
		Address userAddress = new Address("dummy stree", "tx", "la", "85151");		
		UserDetails3 user3 = new UserDetails3("user name", userAddress);
		System.out.println(user3.toString());
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user3);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	
	public static void demoPrimaryId() {
		
		
		System.out.println("inserting user details to user_details2 with auto id generation");
		UserDetails2 user2 = new UserDetails2("First user", new Date(),"First user address", "first user description" );
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user2);
		session.getTransaction().commit();	
		System.out.println(user2.toString());		
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoReterive() {

		Session session = sessionFactory.openSession();		
		
		//read from db
		session.beginTransaction();
		
		System.out.println("reteriving user details for user id " + currentUserId);		
		UserDetails user = (UserDetails)session.get(UserDetails.class, currentUserId);		
		System.out.println(user.toString());
		
		session.getTransaction().commit();	
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
		
	}
	
	public static void demoInsert() {
		
		
		System.out.println("inserting user details for user id " + currentUserId);
		UserDetails user = new UserDetails(currentUserId, "First user", new Date(),"First user address", "first user description" );
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();	
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void setupSessionFactoryDB() {
		
		sessionFactory = new Configuration().configure().buildSessionFactory();
		System.out.println("\n-----------------------------------------------------------------------------\n");
		System.out.println("connecting to database using url : "
					+ sessionFactory.getProperties().get("hibernate.connection.url"));
		System.out.println("-----------------------------------------------------------------------------\n");

	
	}

}

