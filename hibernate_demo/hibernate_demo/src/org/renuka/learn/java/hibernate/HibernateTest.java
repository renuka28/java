package org.renuka.learn.java.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.renuka.learn.java.hibernate.dto.UserDetails;

public class HibernateTest {

	private static SessionFactory sessionFactory = null;
	public static void main(String[] args) {
		
		
		setupSessionFactoryDB();
		demoInsert();
		demoReterive();

	}
	
	public static void demoReterive() {

		Session session = sessionFactory.openSession();		
		
		//read from db
		session.beginTransaction();
		int userid = 1;
		System.out.println("reteriving user details for user id " + userid);
		
		UserDetails user = (UserDetails)session.get(UserDetails.class, userid);		
		System.out.println(user.toString());
		session.getTransaction().commit();	
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
		
	}
	
	public static void demoInsert() {
		
		int userid = 1;
		System.out.println("inserting user details for user id " + userid);
		UserDetails user = new UserDetails(1, "First user", new Date(),"First user address", "first user description" );
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
