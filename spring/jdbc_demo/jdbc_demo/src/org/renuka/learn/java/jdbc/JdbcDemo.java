package org.renuka.learn.java.jdbc;

import org.renuka.learn.java.jdbc.dao.JdbcDaoImpl;
import org.renuka.learn.java.jdbc.dao.JdbcDaoImplBasic;
import org.renuka.learn.java.jdbc.model.Circle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//basic jdbc demo
//		demoBasicJDBC();
//		//demo jdbc using spring
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc.xml");
//		demoBasicSpringJDBC(ctx);
//		demoBasicSpringJDBCWithDBCP();
		demoBasicSpringJDBCTempate();

	}
	
	public static void demoBasicSpringJDBCTempate() {	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_dbcp.xml");
		JdbcDaoImpl dao = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);		
		System.out.println("respose from Spring JDBC template getCircleCount ");
		System.out.println("Total count of Circles = " + dao.getCircleCount());
		System.out.println("---------------------------------------------------------\n");

	}
	public static void demoBasicSpringJDBCWithDBCP() {	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_dbcp.xml");
		JdbcDaoImpl dao = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
		Circle circle = dao.getCircle(3);
		System.out.println("respose from Spring JDBC implementation usnig org.apache.commons.dbcp2.BasicDataSource");
		System.out.println(circle.getName());
		System.out.println("---------------------------------------------------------\n");

	}
	
	public static void demoBasicSpringJDBC(ApplicationContext ctx) {	
		JdbcDaoImpl dao = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
		Circle circle = dao.getCircle(2);
		System.out.println("respose from Spring JDBC implementation using org.springframework.jdbc.datasource.DriverManagerDataSource ");
		System.out.println(circle.getName());
		System.out.println("---------------------------------------------------------\n");
	}

	public static void demoBasicJDBC() {
		Circle circle = new JdbcDaoImplBasic().getCircle(1);		
		System.out.println("respose from basic basic JDBC implementation ");
		System.out.println(circle.getName());
		System.out.println("---------------------------------------------------------\n");
	}
}
