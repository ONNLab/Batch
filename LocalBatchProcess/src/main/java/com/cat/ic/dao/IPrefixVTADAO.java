package com.cat.ic.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cat.ic.model.PrefixVTA;

public interface IPrefixVTADAO {
	public List<PrefixVTA> getPrefix()  throws DataAccessException, ParseException;
}
