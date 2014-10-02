package com.cat.ic.service.impl;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.cat.ic.dao.IPrefixVTADAO;
import com.cat.ic.model.PrefixVTA;
import com.cat.ic.model.Status;
import com.cat.ic.service.IPrefixVTAService;

public class PrefixVTAServiceImpl implements IPrefixVTAService {
	private final Logger log = LoggerFactory.getLogger(PrefixVTAServiceImpl.class);
	private IPrefixVTADAO prefixDAO;

	public void setPrefixDAO(IPrefixVTADAO prefixDAO) {
		this.prefixDAO = prefixDAO;
	}

	@Override
	public List<PrefixVTA> getPrefix() throws DataAccessException,
			ParseException {
		return prefixDAO.getPrefix();
	}

	@Override
	public String getService(final String numbering) {
		String cnumber = null;
		String service = null;
		if (!numbering.startsWith("66")) {
			try {
				List<PrefixVTA> prefix = getPrefix();
				for (PrefixVTA p : prefix) {
					int l = p.getPrefix().length();
					if (l < numbering.length()) {
						cnumber = numbering.substring(0, l);
					} else {
						cnumber = numbering;
					}
					log.trace(" COMPARE bno=" + cnumber + " WITH PREFIX "
							+ p.getPrefix());
					if (cnumber.equalsIgnoreCase(p.getPrefix())) {
						
						log.debug("cnumber length = " + cnumber.length() + " numbering lenght ="+numbering.length() + " numbering="+numbering);
						if ((cnumber.length() < numbering.length())
								&& (! numbering.startsWith("1800"))) {

							service = p.getBearerService()
									+ "|"
									+ numbering
											.substring(l, numbering.length());
							log.debug("FOUND and return with case 1 service = ["
									+ service + "]");
						} else if ((cnumber.length() < numbering.length())
								&& numbering.startsWith("1800")) {

							service = p.getBearerService() + "|" + numbering;
							log.debug("FOUND and return with case 2 service = ["
									+ service + "]");
						} else {
							service = p.getBearerService() + "|66" + numbering;
							log.debug("FOUND and return with case 3 service = ["
									+ service + "]");
						}
						break;
					} else {
						service = Status.UNKNOWN.toString() + "|"
								+ Status.UNKNOWN.toString();
					}
				}
			} catch (DataAccessException e) {
				log.error(e.getMessage());
				service = Status.UNKNOWN.toString() + "|"
						+ Status.UNKNOWN.toString();
			} catch (ParseException e) {
				log.error(e.getMessage());
				service = Status.UNKNOWN.toString() + "|"
						+ Status.UNKNOWN.toString();
			}
			return service;
		} else
			return null;
	}

	@Override
	public String getServiceAndRemovePrefix(String numbering) {
		// TODO Auto-generated method stub
		return null;
	}

}
