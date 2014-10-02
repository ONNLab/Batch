package com.cat.ic.service;

import java.text.ParseException;

import org.springframework.dao.DataAccessException;

import com.cat.ic.model.Route;

public interface IRouteService {
	public Route getRouteInfoByRouteName(String routeName,String cdrDate) throws DataAccessException, ParseException;
}
