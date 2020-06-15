package org.renuka.learn.java.jdbc;

import java.util.List;

import org.renuka.learn.java.jdbc.dao.JdbcDaoImpl;

import org.renuka.learn.java.jdbc.dao.JdbcDaoImplBasic;
import org.renuka.learn.java.jdbc.dao.JdbcTemplateDaoImpl;
import org.renuka.learn.java.jdbc.dao.MySimpleNamedJdbcDaoImpl;
import org.renuka.learn.java.jdbc.model.Circle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcDemo {

	public static void main(String[] args) {		
		//basic jdbc demo
		demoBasicJDBC();
		//demo jdbc using spring
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc.xml");
		demoBasicSpringJDBC(ctx);
		demoBasicSpringJDBCWithDBCP();
		demoBasicSpringJDBCTemplate();
		demoBasicSpringJDBCTemplateRowmapper();
		demoBasicSpringJDBCTemplateRowmapperList();
		demoBasicSpringJDBCTemplateInsert();
		demoBasicSpringJDBCTemplateDDLCreateTable();
		demoSpringNamedParameterJDBCTemplateInsert();
		demoSpringNamedJDBCDao();
	}
	
	
	public static void demoSpringNamedJDBCDao() {
	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
		MySimpleNamedJdbcDaoImpl dao = ctx.getBean("mySimpleNamedJdbcDaoImpl", MySimpleNamedJdbcDaoImpl.class);		
		System.out.println("response from Spring MySimpleNamedJdbcDaoImpl.getCircleCount ");
		System.out.println("Total count of Circles using MySimpleNamedJdbcDaoImpl.getCircleCount = " + dao.getCircleCount());
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();
	}
	

	public static void demoSpringNamedParameterJDBCTemplateInsert() {	
			
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
			JdbcTemplateDaoImpl dao = ctx.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);
			
			int newCircleId = dao.getMaxCircleId() + 1;
			String newCircleName = newCircleId + " Circle";
			Circle circle = new Circle(newCircleId, newCircleName);
			System.out.println("Using NamedParemeterJDBCTemplate inserting into Circle table values id = '" + circle.getId() +
					"' name = '" + circle.getName() +"'");
			dao.insertCircle2(circle);
			System.out.println("inserted using NamedParemeterJDBCTemplate ... retrieving values for confirmation");
			
			circle = dao.getCircle2(newCircleId); 
			System.out.println("reterived id = '" + circle.getId() +
					"' name = '" + circle.getName().strip() +"'");	
			System.out.println("-----------------------------------------------------------------------------------------\n");
			((ClassPathXmlApplicationContext) ctx).close();
	}
	
	public static void demoBasicSpringJDBCTemplateDDLCreateTable() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
		JdbcTemplateDaoImpl dao = ctx.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);
		JdbcTemplate jdbcTemplate = dao.getJdbcTemplate();
		String sql = "CREATE TABLE TRIANGLE (ID INTEGER, NAME VARCHAR(50))";
		try {
			jdbcTemplate.execute(sql);			
		}catch(Exception e) {
			System.out.println("table probably exists. Check database");
			System.out.println(e);
		}
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();
		
	}

	
	public static void demoBasicSpringJDBCTemplateInsert() {	
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
		JdbcTemplateDaoImpl dao = ctx.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);
		
		int newCircleId = dao.getMaxCircleId() + 1;
		String newCircleName = newCircleId + " Circle";
		Circle circle = new Circle(newCircleId, newCircleName);
		System.out.println("inserting into Circle table values id = '" + circle.getId() +
				"' name = '" + circle.getName() +"'");
		dao.insertCircle(circle);
		System.out.println("inserted... retrieving values for confirmation");
		
		circle = dao.getCircle2(newCircleId); 
		System.out.println("reterived id = '" + circle.getId() +
				"' name = '" + circle.getName().strip() +"'");	
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();
	}
	
	public static void demoBasicSpringJDBCTemplateRowmapperList() {	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
		JdbcTemplateDaoImpl dao = ctx.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);
		System.out.println("response from Circle.getAllCircles() using RowMapper with List<Circle> as a list");
		
		List<Circle> allCircles = dao.getAllCircles();
		for(Circle circle:allCircles) {			
			System.out.println("name of the circle whose id is '" + circle.getId() + 
					"' is '" + circle.getName().strip() + "'");	
		}
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();
	}
	
	
	public static void demoBasicSpringJDBCTemplateRowmapper() {	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
		JdbcTemplateDaoImpl dao = ctx.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);
		System.out.println("response from Circle.getCircle2() using RowMapper");		
		int minId = dao.getMinCircleId();
		int maxId = dao.getMaxCircleId();
		System.out.println("circle ids varies from '" + minId + "' to '" + maxId + "'");
		System.out.println("fetching circle from ids...");
		for(int i=minId; i<= maxId; i++) {
			Circle circle = dao.getCircle2(i);
			System.out.println("name of the circle whose id is '" + circle.getId() + 
					"' is '" + circle.getName().strip() + "'");	
		}
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();
	}
	
	
	
	public static void demoBasicSpringJDBCTemplate() {	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_template.xml");
		JdbcTemplateDaoImpl dao = ctx.getBean("jdbcTemplateDaoImpl", JdbcTemplateDaoImpl.class);		
		System.out.println("response from Spring JDBC template getCircleCount ");
		System.out.println("Total count of Circles = " + dao.getCircleCount());
		System.out.println("-----------------------------------------------------------------------------------------\n");
		int minId = dao.getMinCircleId();
		int maxId = dao.getMaxCircleId();
		System.out.println("circle ids varies from '" + minId + "' to '" + maxId + "'");
		
		for(int i=minId; i<= maxId; i++) {
			System.out.println("name of the circle whose id is '" + i + 
					"' is '" + dao.getCircleName(i).strip() + "'");	
		}
		
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();

	}	

	public static void demoBasicSpringJDBCWithDBCP() {	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jdbc_dbcp.xml");
		JdbcDaoImpl dao = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
		Circle circle = dao.getCircle(3);
		System.out.println("response from Spring JDBC implementation usnig org.apache.commons.dbcp2.BasicDataSource");
		System.out.println(circle.getName().strip());
		System.out.println("-----------------------------------------------------------------------------------------\n");
		((ClassPathXmlApplicationContext) ctx).close();		
	}
	
	public static void demoBasicSpringJDBC(ApplicationContext ctx) {	
		JdbcDaoImpl dao = ctx.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
		Circle circle = dao.getCircle(2);
		System.out.println("response from Spring JDBC implementation using org.springframework.jdbc.datasource.DriverManagerDataSource ");
		System.out.println(circle.getName().strip());
		System.out.println("-----------------------------------------------------------------------------------------\n");
	}

	public static void demoBasicJDBC() {
		Circle circle = new JdbcDaoImplBasic().getCircle(1);		
		System.out.println("response from basic basic JDBC implementation ");
		System.out.println(circle.getName().strip());
		System.out.println("-----------------------------------------------------------------------------------------\n");
	}
}
