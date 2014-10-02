package com.cat.ic.service.impl;

import java.text.ParseException;

import org.springframework.dao.DataAccessException;

import com.cat.ic.dao.IRouteDAO;
import com.cat.ic.model.Route;
import com.cat.ic.service.IRouteService;

public class RouteServiceImpl implements IRouteService {
	private IRouteDAO routeDAO;

	public void setRouteDAO(IRouteDAO routeDAO) {
		this.routeDAO = routeDAO;
	}

	@Override
	public Route getRouteInfoByRouteName(String routeName, String cdrDate)
			throws DataAccessException, ParseException {
		return routeDAO.getRouteInfoByRouteName(routeName, cdrDate);
	}

}
