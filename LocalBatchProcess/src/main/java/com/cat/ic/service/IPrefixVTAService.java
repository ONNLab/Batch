package com.cat.ic.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cat.ic.model.PrefixVTA;

public interface IPrefixVTAService {
	public List<PrefixVTA> getPrefix() throws DataAccessException, ParseException;
	public String getService(String numbering);
	public String getServiceAndRemovePrefix(String numbering);
}
