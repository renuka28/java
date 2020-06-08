package com.renuka.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBCExecutor {
    public static void main(String[] args) {
        try{
            Connection connection = getDBConnection("");
            //create
            createCustomer(connection);
            //find by id
            findCustomer(connection);
            //update
            updateCustomer(connection);
            //All including delete
            crudCustomer(connection);
            //get order using statement
            getOrderByID(connection);
            //get order using stored procedure
            getOrderByIDStoreProc(connection);
            //find all using sorted order
            findAllSorted(connection);
            //find all using paging mechanism
            findAllPaged(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getDBConnection(String host) {
        //provide user id and password
        DatabaseConnectionManager dcm = null;
        String server = "localhost";
        String userName = "";
        String password = "";
        String dbName = "hplussport";
        Connection connection = null;

        if(host == "aws"){
            server = "";
            userName = "";
            password = "";
        }
        System.out.println("connecting to " +server);
        dcm = new DatabaseConnectionManager(server, dbName, userName, password);

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public static void createCustomer(Connection connection){
        CustomerDAO customerDAO = new CustomerDAO(connection);
        Customer customer = new Customer();
        customer.setFirstName("George");
        customer.setLastName("Washington");
        customer.setEmail("george.washington@wh.gov");
        customer.setPhone("(555) 555-6543");
        customer.setAddress("1234 Main St");
        customer.setCity("Mount Vernon");
        customer.setState("VA");
        customer.setZipCode("22121");

        customerDAO.create(customer);
        System.out.println("New customer 'George Washington' created");
    }

    public static void updateCustomer(Connection connection){
        CustomerDAO customerDAO = new CustomerDAO(connection);
        Customer customer = customerDAO.findById(10000);
        System.out.println("found customer and email - " + customer.getFirstName() + " " + customer.getLastName() + " " +
                customer.getEmail());
        String newEmail = "gubol@wh.gov";
        System.out.println("updating email to - " + newEmail);
        customer.setEmail(newEmail);
        customer = customerDAO.update(customer);
        System.out.println("updated information -  " + customer.getFirstName() + " " + customer.getLastName() + " " +
                customer.getEmail());
    }

    public static void findCustomer(Connection connection){
        CustomerDAO customerDAO = new CustomerDAO(connection);
        int id = 1000;
        Customer customer = customerDAO.findById(id);
        System.out.println("found customer " + customer.getFirstName() + " " + customer.getLastName() + "with id " +
                id);
    }

    public static void crudCustomer(Connection connection){
        CustomerDAO customerDAO = new CustomerDAO(connection);
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Adams");
        customer.setEmail("jadams.wh.gov");
        customer.setAddress("1234 Main St");
        customer.setCity("Arlington");
        customer.setState("VA");
        customer.setPhone("(555) 555-9845");
        customer.setZipCode("01234");

        Customer dbCustomer = customerDAO.create(customer);
        System.out.println("created new customer");
        System.out.println(dbCustomer);
        System.out.println("found customer with id " + dbCustomer.getId());
        dbCustomer = customerDAO.findById(dbCustomer.getId());
        System.out.println(dbCustomer);
        System.out.println("updating customer email to john.adams@wh.gov");
        dbCustomer.setEmail("john.adams@wh.gov");
        dbCustomer = customerDAO.update(dbCustomer);
        System.out.println(dbCustomer);
        System.out.println("deleting customer with id " + dbCustomer.getId());
        customerDAO.delete(dbCustomer.getId());
    }

    public static void getOrderByID(Connection connection){
        try{
            System.out.println("finding order by statement");
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void getOrderByIDStoreProc(Connection connection){
        try{
            System.out.println("finding order by stored proc");
            OrderDAO orderDAO = new OrderDAO(connection);
            List<Order> orders = orderDAO.getOrdersForCustomer(789);
            orders.forEach(System.out::println);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void findAllSorted(Connection connection){
        try{
            System.out.println("findAllSorted");
            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.findAllSorted(20).forEach(System.out::println);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void findAllPaged(Connection connection){
        try{
            System.out.println("findAllPaged");
            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.findAllSorted(20).forEach(System.out::println);
            System.out.println("Paged");
            //lets do only 3 pages
            for(int i=1;i<3;i++){
                System.out.println("Page number: " + i);
                customerDAO.findAllPaged(10, i).forEach(System.out::println);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
