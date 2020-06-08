package com.renuka.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnectionManager {

    private final String url;
    private final Properties properties;

    public DatabaseConnectionManager(String host, String databaseName,
                                     String userName, String password){
        this.url = "jdbc:postgresql://"+host+"/"+databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", userName);
        this.properties.setProperty("password", password);
    }

    public Connection getConnection () throws SQLException{
        return  DriverManager.getConnection(this.url, this.properties);
    }

}
