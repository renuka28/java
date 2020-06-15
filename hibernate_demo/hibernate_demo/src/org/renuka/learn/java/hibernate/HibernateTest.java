package org.renuka.learn.java.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.renuka.learn.java.hibernate.dto.UserDetails;

public class HibernateTest {

	public static void main(String[] args) {
	
		UserDetails user = new UserDetails(1, "First user", new Date(),"First user address", "first user description" );
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();	

	}

}
