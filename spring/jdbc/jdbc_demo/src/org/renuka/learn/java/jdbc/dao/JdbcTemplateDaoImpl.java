package org.renuka.learn.java.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.renuka.learn.java.jdbc.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateDaoImpl {
	
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	//features from java 5+
	private SimpleJdbcCall simpleJdbcTemplate;
	
	// internal variable getter and setters
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		//we dont' need the datasource at all. Only JdbcTemplate needs it now. 
		//send it to JdbcTemplate. Only less headache
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
		//this.dataSource = dataSource;
	}

	
	// all Circle getters
	//obsolte now... getCircle2 is better and shorter
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
	
	public List<Circle> getAllCircles(){
		String sql = "SELECT * FROM circle";
		return jdbcTemplate.query(sql, new CircleMapper());		
		
	}
		
	//use rowmapper to map the rows to right members
	public Circle getCircle2(int circleId) {
		String sql = "SELECT * FROM circle where id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, new CircleMapper());
	}
	
	//this will demo using jdbctemplate
	public int getCircleCount() {
		//JdbcTemplate already had datasource which was sent in setDataSource when spring
		//calls it during initilization. We can remove this now
		//jdbcTemplate.setDataSource(getDataSource());
		String sql = "SELECT COUNT(*) from Circle";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public int getMaxCircleId() {
		String sql = "SELECT MAX(id) from Circle";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public int getMinCircleId() {
		String sql = "SELECT MIN(id) from Circle";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public String getCircleName(int circleId) {
		String sql = "SELECT name FROM Circle WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, String.class);
	}
		
	//row mapper used by all getters
	private static final class CircleMapper implements RowMapper<Circle>{

		@Override
		public Circle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Circle circle = new Circle();
			circle.setId(resultSet.getInt("ID"));
			circle.setName(resultSet.getString("NAME"));
			return circle;
		}
	}
	
	//all writers
	public void insertCircle(Circle circle) {
		String sql = "INSERT INTO CIRCLE(ID, NAME) VALUES(?,?)";
		jdbcTemplate.update(sql, new Object[] {circle.getId(),  circle.getName()});
		
	}
	
	//insert circle using named parameters
	public void insertCircle2(Circle circle) {
		
		String sql = "INSERT INTO CIRCLE(ID, NAME) VALUES(:id,:name)";		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", circle.getId());
		namedParameters.addValue("name", circle.getName());
		System.out.println(namedParameters.toString());
		namedParameterJdbcTemplate.update(sql,  namedParameters);
	}
}


/*
 *
//class as it was before it was converted to full fledged spring jdbc template

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


 */
