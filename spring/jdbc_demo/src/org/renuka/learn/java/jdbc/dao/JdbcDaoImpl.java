package org.renuka.learn.java.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.renuka.learn.java.jdbc.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcDaoImpl {
	
	@Autowired
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Circle getCircle(int circleId)	{
		
		Connection conn = null;
		Circle circle = null;
		try {
			String driver = "org.apache.derby.jdbc.ClientDriver";
			//Class.forName(driver).newInstance();
			String connString = "jdbc:derby://localhost:1527/db";	
			conn = DriverManager.getConnection(connString);
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM circle where id = ?");
			ps.setInt(1,  circleId);
			ResultSet rs = ps.executeQuery();			
			if (rs.next()) {
				circle = new Circle(circleId, rs.getString("name"));
			}
			rs.close();
			ps.close();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				conn.close();
			}
			catch(SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
		return circle;		
	}

}

