package com.cat.ic.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cat.ic.model.Element;

public interface IElementDAO {
	public String getOperByNumber(String number,String cdrDate) throws DataAccessException, ParseException;
	public List<Element> getPartialElement() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6680() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6681() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6682() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6683() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6684() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6685() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6686() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6687() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6688() throws DataAccessException, ParseException;
	public List<Element> getPartialElement6689() throws DataAccessException, ParseException;
	public Element getFullElementByNumber(String number,String cdrDate) throws DataAccessException, ParseException;
}
