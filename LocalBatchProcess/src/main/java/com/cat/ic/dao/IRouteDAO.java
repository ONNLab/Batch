package com.cat.ic.dao;

import java.text.ParseException;
import org.springframework.dao.DataAccessException;

import com.cat.ic.model.Route;

public interface IRouteDAO {
	public Route getRouteInfoByRouteName(String routeName,String cdrDate) throws DataAccessException, ParseException;
}
