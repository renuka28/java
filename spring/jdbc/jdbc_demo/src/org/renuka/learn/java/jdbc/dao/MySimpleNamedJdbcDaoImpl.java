package org.renuka.learn.java.jdbc.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class MySimpleNamedJdbcDaoImpl extends NamedParameterJdbcDaoSupport {
	
	public int getCircleCount() {

		String sql = "SELECT COUNT(*) from Circle";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}

}
