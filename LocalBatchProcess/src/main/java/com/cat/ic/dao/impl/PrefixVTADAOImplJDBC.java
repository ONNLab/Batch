package com.cat.ic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cat.ic.dao.IPrefixVTADAO;
import com.cat.ic.model.PrefixVTA;

public class PrefixVTADAOImplJDBC extends JdbcDaoSupport implements IPrefixVTADAO {
	private final Logger log = LoggerFactory.getLogger(PrefixVTADAOImplJDBC.class);
	
	@Override
	@Cacheable(value = "prefix")
	public List<PrefixVTA> getPrefix() throws DataAccessException, ParseException {
		log.info("Load prefix...");
		
		final String sql = "SELECT * FROM NICS3_PREFIX_NUMBER ORDER BY LENGTH(BNO_PREFIX) DESC";
		List<PrefixVTA> prefix = getJdbcTemplate().query(sql,new PrefixMapper());
		
		return prefix;
	}
	
	private class PrefixMapper implements RowMapper<PrefixVTA> {

		@Override
		public PrefixVTA mapRow(ResultSet rs, int rowNum) throws SQLException {
			PrefixVTA prefix = new PrefixVTA();
			prefix.setPrefix(rs.getString("BNO_PREFIX"));
			prefix.setBearerService(rs.getString("BEARER_SERVICE"));
			
			return prefix;
		}
		
	}

}
