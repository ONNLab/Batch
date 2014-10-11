package com.cat.ic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ElementInfo extends JdbcDaoSupport implements IElementDAO{

	private final Logger log = LoggerFactory.getLogger(ElementInfo.class);
	
	private List<Element> elt6680 = new ArrayList<Element>();
	private List<Element> elt6681 = new ArrayList<Element>();
	private List<Element> elt6682 = new ArrayList<Element>();
	private List<Element> elt6683 = new ArrayList<Element>();
	private List<Element> elt6684 = new ArrayList<Element>();
	private List<Element> elt6685 = new ArrayList<Element>();
	private List<Element> elt6686 = new ArrayList<Element>();
	private List<Element> elt6687 = new ArrayList<Element>();
	private List<Element> elt6688 = new ArrayList<Element>();
	private List<Element> elt6689 = new ArrayList<Element>();
	private List<Element> eltPartial = new ArrayList<Element>();
	
	
	
	@Override
	public String getOperByNumber(String number, String cdrDate)
			throws DataAccessException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Element> getPartialElement() throws DataAccessException,
			ParseException {
		return this.eltPartial;
	}

	@Override
	public List<Element> getPartialElement6680() throws DataAccessException,
			ParseException {
		return this.elt6680;
	}

	@Override
	public List<Element> getPartialElement6681() throws DataAccessException,
			ParseException {
		return this.elt6681;
	}

	@Override
	public List<Element> getPartialElement6682() throws DataAccessException,
			ParseException {
		return this.elt6682;
	}

	@Override
	public List<Element> getPartialElement6683() throws DataAccessException,
			ParseException {
		return this.elt6683;
	}

	@Override
	public List<Element> getPartialElement6684() throws DataAccessException,
			ParseException {
		return this.elt6684;
	}

	@Override
	public List<Element> getPartialElement6685() throws DataAccessException,
			ParseException {
		return this.elt6685;
	}

	@Override
	public List<Element> getPartialElement6686() throws DataAccessException,
			ParseException {
		return this.elt6686;
	}

	@Override
	public List<Element> getPartialElement6687() throws DataAccessException,
			ParseException {
		return this.elt6687;
	}

	@Override
	public List<Element> getPartialElement6688() throws DataAccessException,
			ParseException {
		return this.elt6688;
	}

	@Override
	public List<Element> getPartialElement6689() throws DataAccessException,
			ParseException {
		return this.elt6689;
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
	
	private void load6680() {
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

		this.elt6680 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6681() {
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

		this.elt6681 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6682() {
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

		this.elt6682 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6683() {
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

		this.elt6683 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6684() {
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

		this.elt6684 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6685() {
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

		this.elt6685 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6686() {
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

		this.elt6686 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6687() {
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

		this.elt6687 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6688() {
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

		this.elt6688 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void load6689() {
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

		this.elt6689 = getJdbcTemplate().query(sql,new ElementMapper());
	}
	
	private void loadPartial(){
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

		eltPartial = getJdbcTemplate().query(sql,
				new ElementMapper());
	}
	
	public void init() {
		load6680();
		load6681();
		load6682();
		load6683();
		load6684();
		load6685();
		load6686();
		load6687();
		load6688();
		load6689();
		loadPartial();
	}
}
