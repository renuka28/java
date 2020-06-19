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
		
		demoInsert();
		demoReterive();
		demoPrimaryId();
		demoEmbeddedValueType();
		demoEmbeddedValueType2();
		demoList();
		demoListWithUserList6();
		demoLazyInitializationWithUserList7();
		demoOneToOneMapping();
		demoOneToManyToOneMapping();
		demoOneToManyToManyMapping();
		demoCascadeType();
		
		//inheritance
		demoSingleTableInheritance();
		demoTablePerClassInheritance();
		demoJoinedInheritance();
		
		//CRUD
		demoCrud();
		
		demoTransientPersistentDetached();
		demoPersistDetachedObjects();
		
		//HQL
		demoHQL();
		demoSelectAndPagination();
		demoParameterBindingAndSQLInjection();
		demoNamedQueries();
		
		//Criteria API
		demoCriteriaAPI();
		
		writeSummary();
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void demoCriteriaAPI(){
		Session session = sessionFactory.openSession();
		System.out.println("demoCriteriaAPI - this method demos using Criteria API ");	
		
		int maxId = -1, totalRecords = 25,  pageStart = 0;		
		
		//add some records for playing around
		addSomeRecords(session, totalRecords);
		
		String userNameWIthMaxId = getUserWithMaxId(session).getUserName();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(UserDetailsCrud.class);
		criteria.add(Restrictions.eq("userName", userNameWIthMaxId));
		List<UserDetailsCrud> users = (List<UserDetailsCrud>)criteria.list();
		System.out.println("user with userName '" + userNameWIthMaxId + "' is " + users.get(0) );
		
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
	public static void demoHQL(){
		Session session = sessionFactory.openSession();
		System.out.println("demoHQL - this method demos using HQL and Query Object ");	
		
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



