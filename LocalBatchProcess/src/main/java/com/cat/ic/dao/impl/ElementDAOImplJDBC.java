package com.cat.ic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cat.ic.dao.IElementDAO;
import com.cat.ic.model.Element;

public class ElementDAOImplJDBC extends JdbcDaoSupport implements IElementDAO {
	private final Logger log = LoggerFactory.getLogger(ElementDAOImplJDBC.class);
	
	@Override
	@Cacheable(value = "oper")
	public String getOperByNumber(String number, String cdrDate)
			throws DataAccessException, ParseException {
		final String sql = "SELECT GET_OWNER(?,?) FROM DUAL ";
		log.debug("getOperByNumber [" + number + "],["+cdrDate +"]");
		List<String> numberOper = getJdbcTemplate().query(
				sql,
				new Object[] { number,	cdrDate },
				new int[] { Types.VARCHAR, Types.VARCHAR },
				new RowMapper<String>() {
					public String mapRow(ResultSet resultSet, int i)
							throws SQLException {
						return resultSet.getString(1);
					}
				});

		if (numberOper.isEmpty()) {
			return "UNKNOWN";
		} else
			return numberOper.get(0);

	}

	@Override
	@Cacheable(value = "elements")
	public List<Element> getPartialElement() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS elements...");
		final String sql = "select B.BND_NAME,ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' "
				+ "AND ELT_DIGITS NOT LIKE '668%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	private class ElementMapper implements RowMapper<Element> {

		@Override
		public Element mapRow(ResultSet rs, int rowNum) throws SQLException {
			Element element = new Element();
			element.setEltName(rs.getString("ELT_NAME"));
			element.setEltDigits(rs.getString("ELT_DIGITS"));
			element.setEltFromDttm(rs.getString("ELT_FROM_DTTM"));
			element.setEltToDttm(rs.getString("ELT_TO_DTTM"));
			element.setEltDesc(rs.getString("EST_DESC"));
			return element;
		}

	}

	@Override
	@Cacheable(value = "element")
	public Element getFullElementByNumber(String number, String cdrDate)
			throws DataAccessException, ParseException {
		log.debug("Query full elements digits=["+number+"]");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'F' "
				+ "AND ELT_DIGITS = ? "
				+ "AND ELT_FROM_DTTM <= ? AND ELT_TO_DTTM >= ?" +
				" AND ROWNUM<=1";
		try {
			Element element = (Element) getJdbcTemplate()
					.queryForObject(
							sql,
							new Object[] {
									number,
									new SimpleDateFormat("yyyyMMdd")
											.parse(cdrDate),
									new SimpleDateFormat("yyyyMMdd")
											.parse(cdrDate) },
							new int[] { Types.VARCHAR, Types.TIMESTAMP,
									Types.TIMESTAMP }, new ElementMapper());
			return element;
		} catch (final EmptyResultDataAccessException e) {
			logger.debug("EmptyResultDataAccessException Warning : number "+number);
			return null;
		}

	}

	@Override
	@Cacheable(value = "elements6680")
	public List<Element> getPartialElement6680() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6680 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6680%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6681")
	public List<Element> getPartialElement6681() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6681 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6681%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6682")
	public List<Element> getPartialElement6682() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6682 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6682%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6683")
	public List<Element> getPartialElement6683() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6683 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6683%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6684")
	public List<Element> getPartialElement6684() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6684 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6684%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6685")
	public List<Element> getPartialElement6685() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6685 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6685%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6686")
	public List<Element> getPartialElement6686() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6686 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6686%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6687")
	public List<Element> getPartialElement6687() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6687 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6687%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6688")
	public List<Element> getPartialElement6688() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6688 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6688%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

	@Override
	@Cacheable(value = "elements6689")
	public List<Element> getPartialElement6689() throws DataAccessException,
			ParseException {
		log.info("load all PARTIALS PREFIX 6689 elements...");
		final String sql = "select B.BND_NAME ELT_NAME,ELT_DIGITS,ELT_FROM_DTTM,ELT_TO_DTTM,EST_DESC "
				+ "from NICS3_ELEMENT E ,NICS3_BAND B ,NICS3_ELEMENT_SET EST "
				+ "where E.BND_ID = B.BND_ID "
				+ "AND B.EST_ID = EST.EST_ID "
				+ "AND B.BND_DELETE_FL = 'N' "
				+ "AND EST.EST_DELETE_FL = 'N' "
				+ "AND E.ELT_DELETE_FL = 'N' "
				+ "AND ELT_MATCH_TYPE = 'P' " 
				+ "AND ELT_DIGITS LIKE '6689%'"
				+ "ORDER BY length(ELT_DIGITS) DESC";

		List<Element> elements = getJdbcTemplate().query(sql,
				new ElementMapper());
		return elements;
	}

}
