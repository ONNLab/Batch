package com.cat.ic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cat.ic.dao.IRouteDAO;
import com.cat.ic.model.Route;

public class RouteDAOImplJDBC extends JdbcDaoSupport implements IRouteDAO {

	@Override
	@Cacheable(value="route")
	public Route getRouteInfoByRouteName(String routeName, String cdrDate) throws DataAccessException, ParseException {
		final String sql = " SELECT R.RUT_CODE,RG.RUG_NAME,ETIS.ETG_DESC,R.RUT_FROM_DTTM,R.RUT_TO_DTTM "+
							" FROM NICS3_ROUTE R,NICS3_ROUTE_GROUP RG,NICS3_ETIS_ROUTE_GROUP ETIS "+
							" WHERE R.RUG_ID = RG.RUG_ID "+
							" AND RG.ETG_ID = ETIS.ETG_ID "+
							" AND R.RUT_DELETE_FL = 'N' "+
							" AND RG.RUG_DELETE_FL = 'N' "+
							" AND ETIS.ETG_DELETE_FL = 'N'" +
							" AND R.RUT_CODE = ? " +
							" AND R.RUT_FROM_DTTM <= ? " +
							" AND R.RUT_TO_DTTM >= ?" +
							" AND ROWNUM<=1";
		try {
		Route routeInfo = (Route) getJdbcTemplate().queryForObject(sql,
				new Object[] { routeName , new SimpleDateFormat("yyyyMMdd").parse(cdrDate),new SimpleDateFormat("yyyyMMdd").parse(cdrDate)},
		        new int[] { Types.VARCHAR, Types.TIMESTAMP ,Types.TIMESTAMP},
				new RouteMapper());
			return routeInfo;
		} catch (final EmptyResultDataAccessException e) {
			logger.debug("EmptyResultDataAccessException Warning : routeName "+routeName);
			return null;
		}
	}
	
	private class RouteMapper implements RowMapper<Route> {

		@Override
		public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
			Route r = new Route();
			r.setRutCode(rs.getString("RUT_CODE"));
			r.setRugName(rs.getString("RUG_NAME"));
			r.setEtgDesc(rs.getString("ETG_DESC"));
			r.setRutFromDttm(rs.getString("RUT_FROM_DTTM"));
			r.setRutToDttm(rs.getString("RUT_TO_DTTM"));
			return r;
		}

	}

}

