package org.renuka.learn.java.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.renuka.learn.java.hibernate.dto.Address;
import org.renuka.learn.java.hibernate.dto.Address2;
import org.renuka.learn.java.hibernate.dto.FourWheelerJoinedTable;
import org.renuka.learn.java.hibernate.dto.FourWheelerSingleTable;
import org.renuka.learn.java.hibernate.dto.FourWheelerTablePerClass;
import org.renuka.learn.java.hibernate.dto.TwoWheelerJoinedTable;
import org.renuka.learn.java.hibernate.dto.TwoWheelerSingleTable;
import org.renuka.learn.java.hibernate.dto.TwoWheelerTablePerClass;
import org.renuka.learn.java.hibernate.dto.UserDetails;
import org.renuka.learn.java.hibernate.dto.UserDetails2;
import org.renuka.learn.java.hibernate.dto.UserDetails3;
import org.renuka.learn.java.hibernate.dto.UserDetails4;
import org.renuka.learn.java.hibernate.dto.UserDetails5;
import org.renuka.learn.java.hibernate.dto.UserDetails6;
import org.renuka.learn.java.hibernate.dto.UserDetails7;
import org.renuka.learn.java.hibernate.dto.UserDetailsCascadeTypes;
import org.renuka.learn.java.hibernate.dto.UserDetailsCrud;
import org.renuka.learn.java.hibernate.dto.UserDetailsManyToMany;
import org.renuka.learn.java.hibernate.dto.UserDetailsOneToManyToOneMapping;
import org.renuka.learn.java.hibernate.dto.UserDetailsOneToOneMapping;
import org.renuka.learn.java.hibernate.dto.Vehicle;
import org.renuka.learn.java.hibernate.dto.VehicleCascadeTypes;
import org.renuka.learn.java.hibernate.dto.VehicleJoinedTable;
import org.renuka.learn.java.hibernate.dto.VehicleManyToMany;
import org.renuka.learn.java.hibernate.dto.VehicleOneToMany;
import org.renuka.learn.java.hibernate.dto.VehicleSingleTable;
import org.renuka.learn.java.hibernate.dto.VehicleTablePerClass;

public class HibernateTest {
	private static SessionFactory sessionFactory = null;
	private static int currentUserId = 1;
	private static int recordPerPage = 5;
	
	
	public static void main(String[] args) {		
		 
		//basic setup
		setupSessionFactoryDB();
		
		//basic operation
		demoBasicOperations();
		
		//relationship mapping
		demoRelationShipMapping();
		
		//inheritance
		demoInheritance();		
		
		//CRUD
		demoCrud();
		
		//persistent objects
		demoTransientPersistentDetached();
		demoPersistDetachedObjects();
		
		//HQL
		demoHQL();
		
		//Criteria API
		demoCriteria();
	
		//Hibernate Cache
		demoHibernateDefaultCache();
		demoHibernateSecondLevelCache();
		
		writeSummary();
	}
	
	public static void demoBasicOperations() {
		
		demoInsert();
		demoReterive();
		demoPrimaryId();
		demoEmbeddedValueType();
		demoEmbeddedValueType2();
		demoList();
		demoListWithUserList6();
		demoLazyInitializationWithUserList7();	
	}
	
	public static void demoRelationShipMapping() {
			
		demoOneToOneMapping();
		demoOneToManyToOneMapping();
		demoOneToManyToManyMapping();
		demoCascadeType();
	}
	
	public static void demoInheritance() {
		
		demoSingleTableInheritance();
		demoTablePerClassInheritance();
		demoJoinedInheritance();
	}
	
	public static void demoHQL() {
		demoHQLBasicFeatures();
		demoSelectAndPagination();
		demoParameterBindingAndSQLInjection();
		demoNamedQueries();
	}
	
	public static void demoCriteria() {
		demoCriteriaAPIBasics();
		demoCriteriaProjections();
		demoCriteriaQueryByExample();
		
	}
	
	
	public static void demoHibernateSecondLevelCache() {
		
		Session session = sessionFactory.openSession();
		System.out.println("\ndemoHibernateSecondLevelCache - this method demos caching in Criteria ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;		
		
		//add some records for playing around
		addSomeRecords(session, totalRecords);
		session.close();
		
		System.out.println("\ndemo second level caching in hibernate - enable second level caching in hibernate.cfg.xml ");
		System.out.println("below session.get results in a select statement");
		session = sessionFactory.openSession();
		session.beginTransaction();
		//we have added 25 records. so there will be a record with id 10
		UserDetailsCrud user1 = (UserDetailsCrud) session.get(UserDetailsCrud.class, 10);
		System.out.println(user1.toString());
		
		System.out.println("if we repeat the same call again it won't result in another sleect statement");		
		UserDetailsCrud user2 = (UserDetailsCrud) session.get(UserDetailsCrud.class, 10);
		System.out.println(user2.toString());
		
		System.out.println("even if we update the user it will only update the DB and won't do a select again");
		user1.setUserName(user1.getUserName() + " - Updated");
		user2 = (UserDetailsCrud) session.get(UserDetailsCrud.class, 10);
		System.out.println(user2.toString());
		
		session.getTransaction().commit();
		session.close();
			
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		System.out.println("\ndoing the same thing again in a new session will force the Hibernate to fetch the data again");		
		user2 = (UserDetailsCrud) session2.get(UserDetailsCrud.class, 10);
		System.out.println(user2.toString());
		System.out.println("we can use second level cache to avoid repeated select statement above");
		session2.getTransaction().commit();
		session2.close();
		
		System.out.println("\nIf we use Query object then we hibernate will not use the cache by default. It requires cache.use.query_cache to be set true. "
				+ "Otherwise there will be two fetches");
		
		Session session3 = sessionFactory.openSession();
		System.out.println("\nFetching using QueryObject");		
		session3.beginTransaction();
		Query query1 = session3.createQuery("from UserDetailsCrud user where userId = 15");
		//now this query is cacheble. This needs to be used along with cache.use.query_cache to be set true
		query1.setCacheable(true);
		List users = query1.list();
		session3.getTransaction().commit();
		session3.close();
		
		Session session4 = sessionFactory.openSession();
		System.out.println("\nFetching using QueryObject");		
		session4.beginTransaction();
		Query query2 = session4.createQuery("from UserDetailsCrud user where userId = 15");
		//now this query is cacheble. This needs to be used along with cache.use.query_cache to be set true
		query2.setCacheable(true);
		users = query2.list();
		session4.getTransaction().commit();
		session4.close();
		
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoHibernateDefaultCache() {
		
		Session session = sessionFactory.openSession();
		System.out.println("\ndemoHibernateDefaultCache - this method demos caching in Criteria ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;		
		
		//add some records for playing around
		addSomeRecords(session, totalRecords);
		session.close();
		
		
		System.out.println("\ndemo caching in hibernate - enable hibernate query printing using <property name=\"show_sql\">true</property> in hibernate.cfg.xml ");
		System.out.println("below session.get results in a select statement");
		session = sessionFactory.openSession();
		session.beginTransaction();
		//we have added 25 records. so there will be a record with id 10
		UserDetailsCrud user1 = (UserDetailsCrud) session.get(UserDetailsCrud.class, 10);
		System.out.println(user1.toString());
		
		System.out.println("if we repeat the same call again it won't result in another sleect statement");		
		UserDetailsCrud user2 = (UserDetailsCrud) session.get(UserDetailsCrud.class, 10);
		System.out.println(user2.toString());
		
		System.out.println("even if we update the user it will only update the DB and won't do a select again");
		user1.setUserName(user1.getUserName() + " - Updated");
		user2 = (UserDetailsCrud) session.get(UserDetailsCrud.class, 10);
		System.out.println(user2.toString());
		
		session.getTransaction().commit();
		session.close();
		
		
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		System.out.println("\ndoing the same thing again in a new session will force the Hibernate to fetch the data again");		
		user2 = (UserDetailsCrud) session2.get(UserDetailsCrud.class, 10);
		System.out.println(user2.toString());
		System.out.println("we can use second level cache to avoid repeated select statement above");
		session2.getTransaction().commit();
		session2.close();
		
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoCriteriaQueryByExample(){
		Session session = sessionFactory.openSession();
		System.out.println("\ndemoProjectionsAndQueryByExample - this method demos Query by Example using depricated Criteria API ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;		
		
		//add some records for playing around
		addSomeRecords(session, totalRecords);
		maxId = getMaxId(session);
		UserDetailsCrud userNameWIthMaxId = getUserWithMaxId(session);
		
		//create an example		
		Example example = Example.create(userNameWIthMaxId);
		System.out.println("\nquery for users using example object " + userNameWIthMaxId);
		System.out.println("since all values are set for the example object it will pull objects which are exactly same as example object");
		Criteria criteria = session.createCriteria(UserDetailsCrud.class)
				.add(example);		
		List<Object> users = criteria.list();
		System.out.println("users pulled using example object...");
		for(Object user:users)
			System.out.println(user);
		
		//query with no value set to primary key
		UserDetailsCrud exampleUser = new UserDetailsCrud();
		exampleUser.setUserName(userNameWIthMaxId.getUserName());
		example = Example.create(exampleUser);
		System.out.println("\nquery for users using example object " + exampleUser);
		System.out.println("Only name is set and it ignores the primary key field and only pulls rows which have same value as it is in name field");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.add(example);		
		users = criteria.list();
		System.out.println("users pulled using example object...");
		for(Object user:users)
			System.out.println(user);
		
		//query with no value set to primary key
		exampleUser = new UserDetailsCrud();
		exampleUser.setUserId(userNameWIthMaxId.getUserId());
		example = Example.create(exampleUser);
		System.out.println("\nquery for users using example object " + exampleUser);
		System.out.println("Only id is set which is the primary key. So hibernate ignores it and since nothing else is set it pulls all records");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.add(example);		
		users = criteria.list();
		System.out.println("users pulled using example object...");
		for(Object user:users)
			System.out.println(user);
		
		
		//create an example		
		example = Example.create(userNameWIthMaxId).excludeProperty("userName");
		System.out.println("\nquery for users using example object " + userNameWIthMaxId);
		System.out.println("We have used object with all values but have choosen to exclude some of the properties(userName here) explicitly. Result is same as previous query");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.add(example);		
		users = criteria.list();
		System.out.println("users pulled using example object...");
		for(Object user:users)
			System.out.println(user);
		
		//create an example		
		exampleUser = new UserDetailsCrud();
		String likeName = userNameWIthMaxId.getUserName().substring(0, userNameWIthMaxId.getUserName().length()-1) + "%";
		exampleUser.setUserName(likeName);
		example = Example.create(exampleUser).enableLike();
		System.out.println("\nquery for users using example object userName = " + likeName);		
		criteria = session.createCriteria(UserDetailsCrud.class)
				.add(example);		
		users = criteria.list();
		System.out.println("users pulled using example object...");
		for(Object user:users)
			System.out.println(user);
		
		session.beginTransaction();
		
		
		session.getTransaction().commit();
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void demoCriteriaProjections(){
		Session session = sessionFactory.openSession();
		System.out.println("demoProjectionsAndQueryByExample - this method demos Projections using depricated Criteria API ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;		
		
		//add some records for playing around
		addSomeRecords(session, totalRecords);
		maxId = getMaxId(session);
		String userNameWIthMaxId = getUserWithMaxId(session).getUserName();
		session.beginTransaction();
		
		System.out.println("Pull a single column using criteria api");
		Criteria criteria = session.createCriteria(UserDetailsCrud.class)
				.setProjection(Projections.property("userId"));		
		criteria.add(Restrictions.ge("userId", maxId - 10));
		List<Object> users = criteria.list();
		for(Object user:users)
			System.out.println(user);
		
		
		System.out.println("\nAggregate functions - Count - using Criteria API Projections API");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.setProjection(Projections.count("userId"));
		users = criteria.list();
		System.out.print("Count of userID = ");
		for(Object user:users)
			System.out.println(user);
		
		System.out.println("\nAggregate functions - Max - using Criteria API Projections API");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.setProjection(Projections.max("userId"));
		users = criteria.list();
		System.out.print("max of userID = ");
		for(Object user:users)
			System.out.println(user);
		
		System.out.println("\nAggregate functions - Sum - using Criteria API Projections API");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.setProjection(Projections.sum("userId"));
		users = criteria.list();
		System.out.print("Sum of userID = ");
		for(Object user:users)
			System.out.println(user);
		
		System.out.println("\nPull a single column using criteria api and sort in decending order");
		criteria = session.createCriteria(UserDetailsCrud.class)
				.setProjection(Projections.property("userId"))	
				.add(Restrictions.ge("userId", maxId - 10))
				.addOrder(Order.desc("userId"));
		users = criteria.list();
		System.out.println("userID in decending order..");
		for(Object user:users)
			System.out.println(user);
		
		
		session.getTransaction().commit();
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void demoCriteriaAPIBasics(){
		Session session = sessionFactory.openSession();
		System.out.println("demoCriteriaAPIBasics - this method demos using Criteria API ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;		
		
		//add some records for playing around
		addSomeRecords(session, totalRecords);
		maxId = getMaxId(session);
		String userNameWIthMaxId = getUserWithMaxId(session).getUserName();
		session.beginTransaction();
		
		System.out.println("Restrictions.ge(\"userId\", maxId - 2)");
		Criteria criteria = session.createCriteria(UserDetailsCrud.class);
		System.out.println(userNameWIthMaxId.substring(0, 10));
		criteria.add(Restrictions.ge("userId", maxId - 2));
		List<UserDetailsCrud> users = (List<UserDetailsCrud>)criteria.list();
		for(UserDetailsCrud user:users)
			System.out.println(user);
		
		System.out.println("\ncriteriaAnd.add(Restrictions.ge(\"userName\", userNameWIthMaxId.substring(0,  10) + \"%\"))\r\n" + 
				"		           .add(Restrictions.ge(\"userId\", maxId - 10))");
		Criteria criteriaAnd = session.createCriteria(UserDetailsCrud.class);
		criteriaAnd.add(Restrictions.ge("userName", userNameWIthMaxId.substring(0,  10) + "%"))
		           .add(Restrictions.ge("userId", maxId - 10));
		users = (List<UserDetailsCrud>)criteriaAnd.list();
		for(UserDetailsCrud user:users)
			System.out.println(user);
		
		System.out.println("\ncriteriaOr.add(Restrictions.or(Restrictions.ge(\"userName\", userNameWIthMaxId.substring(0,  10) + \"%\"))\r\n" + 
				"		           .add(Restrictions.ge(\"userId\", maxId - 10)))");
		Criteria criteriaOr = session.createCriteria(UserDetailsCrud.class);
		criteriaOr.add(Restrictions.or(Restrictions.ge("userName", userNameWIthMaxId.substring(0,  10) + "%"))
		           .add(Restrictions.ge("userId", maxId - 10)));
		users = (List<UserDetailsCrud>)criteriaOr.list();
		for(UserDetailsCrud user:users)
			System.out.println(user);
		
		session.getTransaction().commit();
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static UserDetailsCrud getUserWithMaxId(Session session ) {
		int maxId = getMaxId(session);
		String namedQuery = "UserDetailsCrud.userById";
		
		Query userWithMaxId  = session.getNamedQuery(namedQuery);
		namedQuery = "UserDetailsCrud.userById";
		userWithMaxId  = userWithMaxId.setInteger("userID", maxId);
		System.out.println("executing parameterized query - " + userWithMaxId.getQueryString());
		List<UserDetailsCrud> users = (List<UserDetailsCrud>)userWithMaxId.getResultList();
		return (UserDetailsCrud)users.get(0);
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void demoNamedQueries(){
		
		Session session = sessionFactory.openSession();
		System.out.println("demoNamedQueries - this method demos using Named queries ");	
		System.out.println("calling getMaxId() which uses named queires");
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;
		addSomeRecords(session, totalRecords);
		
		maxId = getMaxId(session);
		System.out.println("Max id among all records in UserDetailsCrud = " + maxId);	

		
		String namedQuery = "UserDetailsCrud.allUsersWithHigerId";
		Query allUsersWithHigerId= session.getNamedQuery(namedQuery);
		allUsersWithHigerId = allUsersWithHigerId.setInteger("userID", maxId/2);
		System.out.println("\nexecuting parameterized named query - " + namedQuery + " -- " + allUsersWithHigerId.getQueryString());
		pageIt(allUsersWithHigerId, pageStart, recordPerPage);
		
		
		System.out.println("\nfetching UserDetailsCrud with max id " + maxId + 
				" using @NamedQuery(name=\"UserDetailsCrud.userById\", query=\"from UserDetailsCrud where userId = :userID\")");	
		String userNameWIthMaxId = getUserWithMaxId(session).getUserName();
		System.out.println("user with max id '" + maxId +"' = " + userNameWIthMaxId);
		
		
		System.out.println("\nfetching user by name using named query - "
				+ "@NamedNativeQuery(name=\"UserDetailsCrud.byName\", query = \"select * from USER_DETAILS_CRUD where username = :userName\", resultClass = UserDetailsCrud.class)");
		String namedNativeQuery = "UserDetailsCrud.byName";
		Query byNme = session.getNamedQuery(namedNativeQuery);		
		byNme = byNme.setString("userName", userNameWIthMaxId);
		System.out.println("executing parameterized named query - " + namedNativeQuery + " -- " + byNme.getQueryString());
		List<UserDetailsCrud> users = (List<UserDetailsCrud>)byNme.getResultList();
		System.out.println(userNameWIthMaxId);		
		System.out.println("user with username '" + userNameWIthMaxId +"' = " + users.get(0).toString());
		
		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void addSomeRecords(Session session, int totalRecords) {
				
		int maxId = getMaxId(session);
		System.out.println("Max id among all records in UserDetailsCrud = " + maxId);	
		//start from next one
		maxId++;
		addUserToUserDetailsCrud(session, maxId,  totalRecords);
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void demoParameterBindingAndSQLInjection(){
		Session session = sessionFactory.openSession();
		System.out.println("demoParameterBindingAndSQLInjection - this method demos parameter binding and SQL Injection using HQL and Query Object ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;
		
		//add some records for further playing around
		addSomeRecords(session, totalRecords);
		maxId = getMaxId(session);
		
		session.beginTransaction();
			
		//example of sql injection . The  user is expected to enter a user id like '5' for which we will query the details
		//instead of that they go ahead and enter an expression. If our program is capaturing the values in a string, as most
		//programs do, then disaster waiting to happen. 
		//intended input
		//String startId = "5";
		
		System.out.println("example of sql injection . The  user is expected to enter a user id like '5' for which we will query the details\r\n" + 
				"instead of that they go ahead and enter an expression. If our program is capaturing the values in a string, as most\r\n" + 
				"programs do, then disaster waiting to happen. \r\n" + 
				"//intended input\r\n" + 
				"String startId = \"5\" \r\n" +
				"//actual input by user\r\n" +
				"String startId = \"5 or 1=1\" \n");
		//what is actually entered
		String startId = "5 or 1=1";
		
		
		String sql = "from UserDetailsCrud where userId=" + startId;
		System.out.println("executing query - " + sql);
		Query queryUserWithUserInput= session.createQuery(sql);
		pageIt(queryUserWithUserInput, pageStart, recordPerPage);
		
		//parametered input using positional parameters
		System.out.println("\nBetter way to do it is using Parameterized queries ");
		sql = "from UserDetailsCrud where userId > ?0";
		startId = "1000";
		queryUserWithUserInput= session.createQuery(sql);
		queryUserWithUserInput = queryUserWithUserInput.setInteger(0, Integer.parseInt(startId));
		System.out.println("executing parameterized query - " + queryUserWithUserInput.getQueryString());
		pageIt(queryUserWithUserInput, pageStart, recordPerPage);
		
		
		//parametered input using named parameters
		System.out.println("\nParameterzed queires using parametrized positional named arugments ");
		sql = "from UserDetailsCrud where userId = :userID";		
		queryUserWithUserInput= session.createQuery(sql);
		maxId = getMaxId(session);
		System.out.println("MAX ID = " + maxId);
		queryUserWithUserInput = queryUserWithUserInput.setInteger("userID", maxId);
		System.out.println("executing parameterized query - " + queryUserWithUserInput.getQueryString());
		pageIt(queryUserWithUserInput, pageStart, recordPerPage);
		
		
		session.getTransaction().commit();
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void demoSelectAndPagination(){
		Session session = sessionFactory.openSession();
		System.out.println("demoSelectAndPagination - this method demos Select and Pagination using HQL and Query Object ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;
		
		//add some records for further playing around
		addSomeRecords(session, totalRecords);
		maxId = getMaxId(session);
		
		session.beginTransaction();
		
		//custom queries and limiting the number of columns
		// we use member variables name instead of column name
		String sql = "select count(*) from UserDetailsCrud";
		System.out.println("executing query - " + sql);
		Query queryGetCount = session.createQuery(sql);
		totalRecords = (int)((long) queryGetCount.getResultList().get(0));
		System.out.println("Total records in UserDetailsCrud = " + totalRecords);
		
			
		
		sql = "select userName from UserDetailsCrud where userId=" + maxId;
		System.out.println("executing query - " + sql);		
		Query queryUserWithMaxId = session.createQuery(sql);
		String userWithMaxId = (String)queryUserWithMaxId.getResultList().get(0);
		System.out.println("User name with Max id '" + maxId + "' is '"  + userWithMaxId);	
							
		//get query object
		//using org.hibernate and not that of jpa
		//standard HQL and pagination 
		
		Query query = session.createQuery("from UserDetailsCrud");
		//setting cursor. this can be used to pagination as shown below
		pageIt(query, pageStart, recordPerPage);
		
		session.getTransaction().commit();
		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static int getMaxId(Session session) {
		//String sql = "select max(userId) from UserDetailsCrud";

		int maxId = -1;
		Query queryGetMax = session.getNamedQuery("UserDetailsCrud.maxUserId");
		System.out.println("executing query - " + queryGetMax.getQueryString());
		if(queryGetMax.getResultList().get(0) != null) {
			maxId = (int) queryGetMax.getResultList().get(0);
		}
		return maxId;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public static void pageIt(Query query, int start, int recordPerPage) {
		
		int totalRecords = query.getResultList().size();
		int pageCount = (int) Math.ceil(totalRecords/(double)recordPerPage);
				
		System.out.println("Total records in the query - " + totalRecords + " and will have " + pageCount + " pages");
		for (int startPage = start; startPage < pageCount ; startPage++) {
			int from = startPage*recordPerPage;
			query.setFirstResult(from);
			query.setMaxResults(recordPerPage);
			
			List<UserDetailsCrud> users = (List<UserDetailsCrud>)query.getResultList();
			int recordsInPage = users.size();
			System.out.println("page starts = " + from + ", to = " + (from + recordsInPage) + " and has " + recordsInPage+ " records" );
			for(int i =0; i < recordsInPage ; i++) {
				
				System.out.println((users.get(i)).toString());
			}	
			
		}
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void demoHQLBasicFeatures(){
		Session session = sessionFactory.openSession();
		System.out.println("demoHQLBasicFeatures - this method demos using HQL and Query Object ");	
		
		int maxId = -1, totalRecords = 10;
		
		//add some records for further playing around
		addSomeRecords(session, totalRecords);
		maxId = getMaxId(session);
		
		
		session.beginTransaction();

		
		//get query object
		//using org.hibernate and not that of jpa
		Query query = session.createQuery("from UserDetailsCrud where userId > 5");		
		List users = query.getResultList();
		System.out.println("size of result = " + users.size());
		System.out.println("users reterived are ...");
		for(int i =0; i < users.size(); i++) {
			
			System.out.println(((UserDetailsCrud)users.get(i)).toString());
		}		
		
		session.getTransaction().commit();
		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoPersistDetachedObjects(){
		Session session = sessionFactory.openSession();
		System.out.println("demoPersistDetachedObjects - this method demos moving detached object to persisting state ");		
		
		//first write a record for future reterival		
		int id = getMaxId(session) + 1;
		UserDetailsCrud userDetails = new UserDetailsCrud(id, "Persistent User");
		System.out.println("saving a demo user for further reterival...");
		System.out.println(userDetails);
		session.beginTransaction();
		session.save(userDetails);
		session.getTransaction().commit();
		session.close();
		
		//reopen session
		session = sessionFactory.openSession();		

		System.out.println("reteriving the saved user with id = " + id);
		session.beginTransaction();//
		UserDetailsCrud userDetailsReterived = session.get(UserDetailsCrud.class, id);
		session.getTransaction().commit();
		session.close();		
		System.out.println("retrived and session closed... ");
		System.out.println(userDetailsReterived);

		
		System.out.println("now session is closed and the the object is in detached state. Lets update its values");
		userDetailsReterived.setUserName("Updated after closing session");
		System.out.println("it can be attached back again calling sesison.update. we will do that now");		
		
		session = sessionFactory.openSession();		
		session.beginTransaction();//
		session.update(userDetailsReterived);
		System.out.println("updating the values now will result in automatic synchronization as the object now is in persistent state");
		userDetailsReterived.setUserName("Updated without calling save or update");
		session.getTransaction().commit();
		session.close();
		
	
		System.out.println("-----------------------------------------------------------------------------\n");
		
	}
	
	
	
	public static void demoTransientPersistentDetached(){
		Session session = sessionFactory.openSession();
		System.out.println("demoTransientPersistentDetached - this method demos Transiient, Persistent and Detached objects");		
		
		UserDetailsCrud userDetailsTransient = new UserDetailsCrud(100, "User Details Transient - This WILL NOT be saved to db as we will"
				+ " not hande over this object to sesson.save() ");
		System.out.println(userDetailsTransient);
		int id = getMaxId(session) + 1;
		UserDetailsCrud userDetailsPersistent = new UserDetailsCrud(id, "User Details Persistent - "
				+ "This will be saved to db and its changes will be tracked until we call session.close()");
		session.beginTransaction();
		session.save(userDetailsPersistent);
		
		System.out.println("\n"  + userDetailsPersistent + "record saved to UserDetailsCrud table successfully....\n");
		
		System.out.println("updating " + userDetailsPersistent + " user name to - 'Persistent object updated without calling session.save()'");
		userDetailsPersistent.setUserName("Won't be saved - only last update saved");
		userDetailsPersistent.setUserName("Persistent object updated without calling session.save()");
		System.out.println("exiting witout calling session.save(). Hibernate will have updated the table");
		
		session.getTransaction().commit();
		session.close();
		
		System.out.println("updating " + userDetailsPersistent + " user name to - 'Detached object updated this WON'T GET SAVED saved as this was done AFTER calling session.save()'");		
		userDetailsPersistent.setUserName("Detached object updated this wont get saved as this was done AFTER calling session.save()");
		System.out.println("exiting. name field should contain 'Persistent object updated without calling session.save()'");
		System.out.println("-----------------------------------------------------------------------------\n");
		
	}
	
	

	public static void demoCrud(){
		Session session = sessionFactory.openSession();
		System.out.println("demoCrud -this method demos basic CRUD operations");		
	
		int startId = 0, endId = 10, totalRecords = (endId- startId);
		addUserToUserDetailsCrud(session, startId,  totalRecords);
		
		//CRUD - reterive
		System.out.println("\nCRUD Reterive - Reteriving "  + totalRecords + " records from UserDetailsCrud table ... ");
		ArrayList<UserDetailsCrud> userList = new ArrayList<UserDetailsCrud>(totalRecords);
		for (int i = startId; i < endId; i++) {
			UserDetailsCrud userDetailsCrud = session.get(UserDetailsCrud.class,  i);
			userList.add(userDetailsCrud);
			System.out.println(userDetailsCrud);
			session.save(userDetailsCrud);
		}
		System.out.println("\n"  + totalRecords + " records reterived from UserDetailsCrud table successfully....");
		
		//CRUD - update
		int updateUpto = endId/2;
		
		System.out.println("\nCRUD update - updating UserDetailsCrud with userid ranging from " 
								+ startId + " up to " + updateUpto  +" by adding strin 'Updated by Hibernate'... ");
		session.beginTransaction();
		for (int i = startId; i < updateUpto; i++) {
			UserDetailsCrud userDetailsCrud = userList.get(i);
			userDetailsCrud.setUserName(userDetailsCrud.getUserName() + " Updated by Hibernate");
			System.out.println("updating - " +userDetailsCrud);
			session.update(userDetailsCrud);
		}
		session.getTransaction().commit();

		System.out.println("\nUserDetailsCrud updated for userids ranging from " 
				+ startId + " to " + updateUpto + "...   and saved to UserDetailsCrud table successfully....");		
		
		//CRUD - delete
		System.out.println("\nCRUD delete - deleting UserDetailsCrud with userid ranging from " 
								+ updateUpto + " to " + endId  +"... ");
		session.beginTransaction();
		for (int i = updateUpto; i < endId; i++) {
			UserDetailsCrud userDetailsCrud = userList.get(i);
			System.out.println("deleting - " +userDetailsCrud);
			session.delete(userDetailsCrud);
		}
		session.getTransaction().commit();
		System.out.println("\nUserDetailsCrud with userid ranging from " 
				+ updateUpto + " to " + endId + "...  deleted from UserDetailsCrud table successfully....");		
		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	//CRUD add user. Carved to act as an helper function to add required number of records to  
	
	public static void addUserToUserDetailsCrud(Session session, int startId, int countOfRecords ){
		
		//CRUD- create
		session.beginTransaction();
		System.out.println("\nCRUD Create - Saving " + countOfRecords + " records UserDetailsCrud table ... ");
		for (int i = startId; i < startId+countOfRecords; i++) {	
			UserDetailsCrud userDetailsCrud = new UserDetailsCrud(i, "User Details for Crud - " + i);	
			System.out.println(userDetailsCrud);				
			session.save(userDetailsCrud);
		
		}
		session.getTransaction().commit();
		System.out.println("\n"  + countOfRecords + " records saved to UserDetailsCrud table successfully....\n");
	}
	
	
	
	public static void demoJoinedInheritance(){
		Session session = sessionFactory.openSession();
		System.out.println("demoJoinedInheritance - this method demos joined table inheritance inheritance."
				+ "\nHibernate will create separate tables for each entity and will store only "
				+ "\n those attributes which are specifi to the derived class in derive entity's table"
				+ "\n. during query, hibernate automatically joins");		
		
		VehicleJoinedTable bus = new VehicleJoinedTable("Bus");
		TwoWheelerJoinedTable splendor = new TwoWheelerJoinedTable("Splendor", "Bike Steering Handle");
		FourWheelerJoinedTable ford = new FourWheelerJoinedTable("Expedition", "Ford steering wheel");	
		
		System.out.println("Saving vehicles - The entities will go to separate tables");
		System.out.println(bus);
		System.out.println(splendor);
		System.out.println(ford + "\n");
		
		session.beginTransaction();
		session.save(bus);
		session.save(splendor);
		session.save(ford);
		session.getTransaction().commit();
		System.out.println("\nVehicles saved successfully....");	
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoTablePerClassInheritance(){
		Session session = sessionFactory.openSession();
		System.out.println("demoTablePerClassInheritance -this method demos table per class inheritance inheritance."
				+ "\nHibernate will create separate tables for each entity");		
		
		VehicleTablePerClass bus = new VehicleTablePerClass("Bus");
		TwoWheelerTablePerClass splendor = new TwoWheelerTablePerClass("Splendor", "Bike Steering Handle");
		FourWheelerTablePerClass ford = new FourWheelerTablePerClass("Expedition", "Ford steering wheel");	
		
		System.out.println("Saving vehicles - The entities will go to separate tables");
		System.out.println(bus);
		System.out.println(splendor);
		System.out.println(ford + "\n");
		
		session.beginTransaction();
		session.save(bus);
		session.save(splendor);
		session.save(ford);
		session.getTransaction().commit();
		System.out.println("\nVehicles saved successfully....");	
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoSingleTableInheritance(){
		Session session = sessionFactory.openSession();
		System.out.println("demoTablePerClassInheritance - this method demos basic single table inheritance inheritance."
				+ "\nHibernate will create a single table with base class names and add additoinal columns to take care of"
				+ "\nadditional variables in the inherited classes");		
		
		VehicleSingleTable bus = new VehicleSingleTable("Bus");
		TwoWheelerSingleTable splendor = new TwoWheelerSingleTable("Splendor", "Bike Steering Handle");
		FourWheelerSingleTable ford = new FourWheelerSingleTable("Expedition", "Ford steering wheel");	
		
		System.out.println("Saving vehicles - The entities will go to a single table ");
		System.out.println(bus);
		System.out.println(splendor);
		System.out.println(ford + "\n");
		
		session.beginTransaction();
		session.save(bus);
		session.save(splendor);
		session.save(ford);
		session.getTransaction().commit();
		System.out.println("\nVehicles saved successfully....");	
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoCascadeType(){
		Session session = sessionFactory.openSession();
		
		System.out.println("demoCascadeType - this method demos CascadingTypes");				
		UserDetailsCascadeTypes userCascadingStyles = new UserDetailsCascadeTypes("UserDetailsManyToMany User 1");
		VehicleCascadeTypes tahoe = new VehicleCascadeTypes("Tahoe");		
		VehicleCascadeTypes ford = new VehicleCascadeTypes("Expedition");	
		
		//add users to vehiciles
		userCascadingStyles.getVehicles().add(tahoe);
		userCascadingStyles.getVehicles().add(ford);
		
		System.out.println(userCascadingStyles +"\n");
		session.beginTransaction();		
		//no more we need to save these two instances as we asked Hibernate to cascade save
//		session.save(tahoe);
//		session.save(ford);
		//change save to persist
//		session.save(userCascadingStyles);
		session.persist(userCascadingStyles);
		session.getTransaction().commit();
		
		System.out.println("\nUserDetailsManyToMany and Vehicle saved successfully....");		
		
		System.out.println("reading from db...");		
		UserDetailsCascadeTypes userCascadingStylesRead = session.get(UserDetailsCascadeTypes.class, userCascadingStyles.getUserId());
		System.out.println("read user details...");
		System.out.println(userCascadingStylesRead.toString());				
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoOneToManyToManyMapping(){
		Session session = sessionFactory.openSession();
		
		System.out.println("demoOneToManyToManyMapping - this method demos many to many entity mapping");				
		
		
		UserDetailsManyToMany usermanyToMany1 = new UserDetailsManyToMany("UserDetailsManyToMany User 1");
		UserDetailsManyToMany usermanyToMany2 = new UserDetailsManyToMany("UserDetailsManyToMany User 2");
		
		VehicleManyToMany tahoe = new VehicleManyToMany("Tahoe");		
		VehicleManyToMany ford = new VehicleManyToMany("Expedition");	
		
		//add users to vehiciles
		usermanyToMany1.getVehicles().add(tahoe);
		usermanyToMany1.getVehicles().add(ford);
		
		usermanyToMany2.getVehicles().add(tahoe);
		usermanyToMany2.getVehicles().add(ford);
		
		tahoe.getUserList().add(usermanyToMany1);
		tahoe.getUserList().add(usermanyToMany2);
		ford.getUserList().add(usermanyToMany1);
		ford.getUserList().add(usermanyToMany2);
		
		
		System.out.println(usermanyToMany1);
		System.out.println(usermanyToMany2 + "\n");
		session.beginTransaction();		
		session.save(usermanyToMany1);
		session.save(usermanyToMany2);
		session.save(tahoe);
		session.save(ford);
		session.getTransaction().commit();
		
		System.out.println("\nUserDetailsManyToMany and Vehicle saved successfully....");		
		
		System.out.println("reading from db...");		
		UserDetailsManyToMany userOneToManyRead = session.get(UserDetailsManyToMany.class, usermanyToMany1.getUserId());
		System.out.println("read user details...");
		System.out.println(userOneToManyRead.toString());
		System.out.println("User of '" + userOneToManyRead.getVehicleAt(0).getVehicleName() + "' is '" 
				+ userOneToManyRead.getVehicleAt(0).getUserAt(0).getUserName() +"'");
		System.out.println("User of '" + userOneToManyRead.getVehicleAt(1).getVehicleName() + "' is '" 
				+ userOneToManyRead.getVehicleAt(0).getUserAt(0).getUserName() +"'");
		System.out.println("User of '" + userOneToManyRead.getVehicleAt(0).getVehicleName() + "' is '" 
				+ userOneToManyRead.getVehicleAt(1).getUserAt(0).getUserName() +"'");
		System.out.println("User of '" + userOneToManyRead.getVehicleAt(1).getVehicleName() + "' is '" 
				+ userOneToManyRead.getVehicleAt(1).getUserAt(1).getUserName() +"'");

		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoOneToManyToOneMapping(){
		Session session = sessionFactory.openSession();
		
		System.out.println("demoOneToManyToOneMapping - this method demos one to many and many to one entity mapping");				
		
		
		UserDetailsOneToManyToOneMapping userOneToMany = new UserDetailsOneToManyToOneMapping();
		userOneToMany.setUserName("UserDetailsOneToManyToOneMapping with one to many and many to entity mapping");
		VehicleOneToMany tahoe = new VehicleOneToMany("Tahoe", userOneToMany);		
		VehicleOneToMany ford = new VehicleOneToMany("Expedition", userOneToMany);		
		
		userOneToMany.getVehicles().add(tahoe);
		userOneToMany.getVehicles().add(ford);
		
		System.out.println(userOneToMany + "\n");
		session.beginTransaction();		
		session.save(userOneToMany);
		session.save(tahoe);
		session.save(ford);
		session.getTransaction().commit();
		
		System.out.println("\nUserDetailsOneToManyToOneMapping and Vehicle saved successfully....");		
		
		System.out.println("reading from db...");		
		UserDetailsOneToManyToOneMapping userOneToManyRead = session.get(UserDetailsOneToManyToOneMapping.class, userOneToMany.getUserId());
		System.out.println("read user details...");
		System.out.println(userOneToManyRead.toString());
		System.out.println("User of '" + userOneToManyRead.getVehicleAt(0).getVehicleName() + "' is '" 
				+ userOneToManyRead.getVehicleAt(0).getUser().getUserName() +"'");
		System.out.println("User of '" + userOneToManyRead.getVehicleAt(1).getVehicleName() + "' is '" 
				+ userOneToManyRead.getVehicleAt(1).getUser().getUserName() +"'");

		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoOneToOneMapping(){
		Session session = sessionFactory.openSession();
		
		System.out.println("demoOneToOneMapping - this method demos one to one entity mapping");				
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleName("Ford expedition");
		UserDetailsOneToOneMapping userOneToOne = new UserDetailsOneToOneMapping(
				"UserDetailsOneToOneMapping with one to one entity mapping",
				vehicle);
		System.out.println(userOneToOne + "\n");
		
		session.beginTransaction();		
		session.save(userOneToOne);
		session.save(vehicle);
		System.out.println("\nuserOneToOne and Vehicle saved successfully....");
		session.getTransaction().commit();
		
		System.out.println("reading from db");
		UserDetailsOneToOneMapping userOneToOneRead = session.get(UserDetailsOneToOneMapping.class, userOneToOne.getUserId());
		System.out.println(userOneToOneRead.toString());
		
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoLazyInitializationWithUserList7() {
		
		Session session = sessionFactory.openSession();
		System.out.println("demoLazyInitializationWithUserList7 - fetching user details to user_details7 which has member "
				+ "variable listOfAddresses"
				+ " as member variable. This will result in lazy initializaiton with the program getting a "
				+ "proxy object.");
		UserDetails7 user7 = new UserDetails7();
				
		user7.setUserName("User7 with variable number of addresses" + "\n");		
		Collection<Address2> listofAddress = GetDummyAddresses();
		user7.getListofAddress().addAll(listofAddress);
		
		
		session.beginTransaction();		
		session.save(user7);
		session.getTransaction().commit();		
		session.close();	
		System.out.println("\nuser7 saved successful. Retriving ....");
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
		
		
		System.out.println("demoListWithUserList6 - inserting user details to user_details6 which stores Address2 using HashSet");		
			UserDetails6 user6 = new UserDetails6();
		user6.setUserName("User6 with variable number of addresses" + "\n");		
		Collection<Address2> listofAddress = GetDummyAddresses();
		user6.getListofAddress().addAll(listofAddress);
		
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user6);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("\nsaved user ..");
		System.out.println(user6);
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoList() {
		
		
		System.out.println("demoList - inserting user details to user_details5 which stores Address2 using HashSet");		
		UserDetails5 user5 = new UserDetails5();
		Collection<Address2> listofAddress = GetDummyAddresses();
		user5.getListofAddress().addAll(listofAddress);
		user5.setUserName("User5 with variable number of addresses" + "\n");		
		
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user5);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("\nsaved user ..");
		System.out.println(user5);
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoEmbeddedValueType2() {
		
		
		System.out.println("demoEmbeddedValueType2 - inserting user details to user_details4 with embedded value types");		
		ArrayList<Address2> listofAddress = GetDummyAddresses();		
		UserDetails4 user4 = new UserDetails4("user4 name", listofAddress.get(0), listofAddress.get(1) );
		
		System.out.println(user4.toString() + "\n");
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user4);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	public static void demoEmbeddedValueType() {
		
		
		System.out.println("demoEmbeddedValueType - inserting user details to user_details3 with embedded value types");		
		Address userAddress = new Address("dummy stree", "tx", "la", "85151");		
		UserDetails3 user3 = new UserDetails3("user name", userAddress);
		System.out.println(user3.toString() + "\n");
		Session session = sessionFactory.openSession();
		//save to db
		session.beginTransaction();
		session.save(user3);
		session.getTransaction().commit();			
		session.close();	
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	
	
	public static void demoPrimaryId() {
		
		
		System.out.println("demoPrimaryId - inserting user details to user_details2 with auto id generation");
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
		
		System.out.println("demoReterive - reteriving user details for user id " + currentUserId);		
		UserDetails user = (UserDetails)session.get(UserDetails.class, currentUserId);		
		System.out.println(user.toString());
		
		session.getTransaction().commit();	
		session.close();
		System.out.println("-----------------------------------------------------------------------------\n");
		
	}
	
	public static void demoInsert() {
		
		
		System.out.println("demoInsert - inserting user details for user id " + currentUserId);
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
	
	public static void writeSummary() {
		
		System.out.println("All data committed to -  : "
					+ sessionFactory.getProperties().get("hibernate.connection.url"));
		System.out.println("-----------------------------------------------------------------------------\n");	
		
	}
	
	
	public static ArrayList<Address2> GetDummyAddresses(){
		
		ArrayList<Address2> listofAddress = new ArrayList<Address2>();
		listofAddress.add(new Address2("dummy home street", "home-tx", "home-la", "home-85151"));				
		listofAddress.add(new Address2("dummy office street", "office-tx", "office-la", "office-85151"));
		listofAddress.add(new Address2("dummy old home street", "old-home-tx", "old-home-la", "old-home-85151"));
		listofAddress.add(new Address2("dummy old-office street", "old-office-tx", "old-office-la", "old-office-85151"));
		return listofAddress;
		
	}

}



