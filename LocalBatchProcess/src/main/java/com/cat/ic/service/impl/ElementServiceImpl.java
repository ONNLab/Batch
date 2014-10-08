package com.cat.ic.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.cat.ic.dao.IElementDAO;
import com.cat.ic.model.Element;
import com.cat.ic.model.Status;
import com.cat.ic.service.IElementService;

public class ElementServiceImpl implements IElementService {
	private IElementDAO elementDAO;
	private final Logger log = LoggerFactory.getLogger(ElementServiceImpl.class);

	public void initialize () throws Exception {
		log.info("load data 6680 to cache");
		elementDAO.getPartialElement6680();
		log.info("load data 6681 to cache");
		elementDAO.getPartialElement6681();
		log.info("load data 6682 to cache");
		elementDAO.getPartialElement6682();
		log.info("load data 6683 to cache");
		elementDAO.getPartialElement6683();
		log.info("load data 6684 to cache");
		elementDAO.getPartialElement6684();
		log.info("load data 6685 to cache");
		elementDAO.getPartialElement6685();
		log.info("load data 6686 to cache");
		elementDAO.getPartialElement6686();
		log.info("load data 6687 to cache");
		elementDAO.getPartialElement6687();
		log.info("load data 6688 to cache");
		elementDAO.getPartialElement6688();
		log.info("load data 6689 to cache");
		elementDAO.getPartialElement6689();
		log.info("load data all other to cache");
		elementDAO.getPartialElement();
	}
	
	public void setElementDAO(IElementDAO elementDAO) {
		this.elementDAO = elementDAO;
	}

	@Override
	public String getOperByNumber(String number, String cdrDate)
			throws DataAccessException, ParseException {
		return elementDAO.getOperByNumber(number, cdrDate);
	}

	@Override
	public List<Element> getPartialElement() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement();
	}

	@Override
	public List<String> getOnwerByAnyNumber(final String number,
			final String cdrDate) throws DataAccessException, ParseException {

		List<String> owner = new ArrayList<String>();
		String unknown = Status.UNKNOWN.toString();

		if (number.startsWith("66")) {

			try {
				Element ef = getFullElementByNumber(number, cdrDate);
				owner.add(ef.getEltName());
				owner.add(ef.getEltDesc());
			} catch (Exception e) {
				owner.add(unknown);
				owner.add(unknown);
			}

			String newNumber = null;
			log.debug("getFullElementByNumber " + number + " OWNER=[" + owner
					+ "]");

			if (owner.get(0).equalsIgnoreCase(unknown) && number.length() > 0
					&& number.startsWith("66")) {

				// List<Element> elements = getPartialElement();
				List<Element> elements = getAllPartialElement(number);
				log.debug("Elements Partial =[" + elements.size() + "]");

				for (Element e : elements) {
					owner.clear();
					int l = e.getEltDigits().length();
					log.debug(e.getEltDigits() + " length = " + l);
					if (l < number.length()) {
						newNumber = number.substring(0, l);
					} else
						newNumber = number;
					log.trace("COMPARE number =[" + newNumber + "] "
							+ "and ELEMENT_DIGITS=[" + e.getEltDigits() + "] "
							+ "and eltfromdttm=" + e.getEltFromDttm()
							+ " compareto " + cdrDate + "and elttodttm="
							+ e.getEltToDttm() + " compareto " + cdrDate);
					if (e.getEltDigits().equalsIgnoreCase(newNumber)
							&& e.getEltFromDttm().compareTo(cdrDate) <= 0
							&& e.getEltToDttm().compareTo(cdrDate) >= 0) {
						owner.add(e.getEltName());
						owner.add(e.getEltDesc());
						log.debug("OWNER OF " + number + "=" + owner);
						break;
					} else {
						owner.add(unknown);
						owner.add(unknown);
					}
				}

				log.debug("PARTIALS DIGITS OWNER=[" + owner + "]");
				return owner;
			} else
				return owner;
		} else {
			owner.add(unknown);
			owner.add(unknown);
			return owner;
		}
	}

	@Override
	public Element getFullElementByNumber(String number, String cdrDate)
			throws DataAccessException, ParseException {
		return elementDAO.getFullElementByNumber(number, cdrDate);
	}

	@Override
	public List<Element> getPartialElement6680() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6680();
	}

	@Override
	public List<Element> getPartialElement6681() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6681();
	}

	@Override
	public List<Element> getPartialElement6682() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6682();
	}

	@Override
	public List<Element> getPartialElement6683() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6683();
	}

	@Override
	public List<Element> getPartialElement6684() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6684();
	}

	@Override
	public List<Element> getPartialElement6685() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6685();
	}

	@Override
	public List<Element> getPartialElement6686() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6686();
	}

	@Override
	public List<Element> getPartialElement6687() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6687();
	}

	@Override
	public List<Element> getPartialElement6688() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6688();
	}

	@Override
	public List<Element> getPartialElement6689() throws DataAccessException,
			ParseException {
		return elementDAO.getPartialElement6689();
	}

	public List<Element> getAllPartialElement(String number)
			throws DataAccessException, ParseException {
		List<Element> e = null;
		if (number.startsWith("6680")) {
			e = getPartialElement6680();
		} else if (number.startsWith("6681")) {
			e = getPartialElement6681();
		} else if (number.startsWith("6682")) {
			e = getPartialElement6682();
		} else if (number.startsWith("6683")) {
			e = getPartialElement6683();
		} else if (number.startsWith("6684")) {
			e = getPartialElement6684();
		} else if (number.startsWith("6685")) {
			e = getPartialElement6685();
		} else if (number.startsWith("6686")) {
			e = getPartialElement6686();
		} else if (number.startsWith("6687")) {
			e = getPartialElement6687();
		} else if (number.startsWith("6688")) {
			e = getPartialElement6688();
		} else if (number.startsWith("6689")) {
			e = getPartialElement6689();
		} else {
			e = getPartialElement();
		}
		return e;
	}
}
