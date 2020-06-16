package org.renuka.learn.java.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

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
import org.renuka.learn.java.hibernate.dto.UserDetails6;
import org.renuka.learn.java.hibernate.dto.UserDetails7;

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
		demoListWithUserList6();
		demoLazyInitializationWithUserList7();
	}
	
	public static void demoLazyInitializationWithUserList7() {
		
		Session session = sessionFactory.openSession();
		System.out.println("fetching user details to user_details7 which has member variable listOfAddresses"
				+ " as member variable. This will result in lazy initializaiton with the program getting a "
				+ "proxy object.");
		UserDetails7 user7 = new UserDetails7();
				
		user7.setUserName("User7 with variable number of addresses");		
		Collection<Address2> listofAddress = GetDummyAddresses();
		user7.getListofAddress().addAll(listofAddress);
		
		
		session.beginTransaction();		
		session.save(user7);
		System.out.println("user7 saving successful. Retriving ....");
		session.getTransaction().commit();		
		session.close();	
		
		session = sessionFactory.openSession();
		//this call will not fetch list of address of default as it is in another table
		//if we use any method that requires the listOfAddresses, then hibernate will fetch as needed
		//this iwll optimize the data retriveal. this is default behaviour and can be modified as needed
		//this is called lazy initialization. First level of member variables are initialized. Opposite is
		//Eager initialization which takes more time and more memory and hibernate will fetch all embedded 
		//method
		UserDetails7 user7Reterived = (UserDetails7) session.get(UserDetails7.class, user7.getUserId());
		session.close();
		
		
		System.out.println("Session closed without using any methods that used listOfAddresses. So it was not reterived. "
				+ "Now trying to fetch values of listOfAddresses will result in exception");
		System.out.println(user7Reterived.getListofAddress().size());
		System.out.println("Exception fixed by adding @ElementCollection(fetch=FetchType.EAGER) to listOfAddress definition"
				+ "in UserDetails7. \nHibernate will fetch everything in this case which will be time consuming in a "
				+ "biggger table/entity");
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoListWithUserList6() {
		
		
		System.out.println("inserting user details to user_details6 which stores Address2 using HashSet");		
			UserDetails6 user6 = new UserDetails6();
		user6.setUserName("User6 with variable number of addresses");		
		Collection<Address2> listofAddress = GetDummyAddresses();
		user6.getListofAddress().addAll(listofAddress);
		
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user6);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("saved user ..");
		System.out.println(user6);
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoList() {
		
		
		System.out.println("inserting user details to user_details5 which stores Address2 using HashSet");		
		UserDetails5 user5 = new UserDetails5();
		Collection<Address2> listofAddress = GetDummyAddresses();
		user5.getListofAddress().addAll(listofAddress);
		user5.setUserName("User5 with variable number of addresses");		
		
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user5);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("saved user ..");
		System.out.println(user5);
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoEmbeddedValueType2() {
		
		
		System.out.println("inserting user details to user_details4 with embedded value types");		
		ArrayList<Address2> listofAddress = GetDummyAddresses();		
		UserDetails4 user4 = new UserDetails4("user4 name", listofAddress.get(0), listofAddress.get(1) );
		
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
	
	public static ArrayList<Address2> GetDummyAddresses(){
		
		ArrayList<Address2> listofAddress = new ArrayList();
		listofAddress.add(new Address2("dummy home street", "home-tx", "home-la", "home-85151"));				
		listofAddress.add(new Address2("dummy office street", "office-tx", "office-la", "office-85151"));
		listofAddress.add(new Address2("dummy old home street", "old-home-tx", "old-home-la", "old-home-85151"));
		listofAddress.add(new Address2("dummy old-office street", "old-office-tx", "old-office-la", "old-office-85151"));
		return listofAddress;
		
	}

}

