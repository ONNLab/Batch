package com.cat.ic.process;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.dao.DataAccessException;

import com.cat.ic.model.CDR;
import com.cat.ic.model.Route;
import com.cat.ic.model.Status;
import com.cat.ic.service.IElementService;
import com.cat.ic.service.IRouteService;

public class CDRItemProcessor implements ItemProcessor<CDR, CDR> {

	private final Logger log = LoggerFactory.getLogger(CDRItemProcessor.class);
	private IRouteService routeService;
	private IElementService elementService;

	public CDRItemProcessor() {
	}

	public void setRouteService(IRouteService routeService) {
		this.routeService = routeService;
	}

	public void setElementService(IElementService elementService) {
		this.elementService = elementService;
	}

	@Override
	public CDR process(CDR item) throws Exception {
		CDR cdr = new CDR();
		log.debug(item.toString());

		if (item.getCdr_bearer_service().contains("VOI")
				|| item.getCdr_bearer_service().contains("SMS")
				|| item.getCdr_bearer_service().contains("MMS")) {
			String cdrDate = item.getCdr_date();
			String cdrTime = item.getCdr_time();
			String cdrDuration = item.getCdr_duration();

			Route inRoute = mapRoute(item.getCdr_in_route(), cdrDate);
			String cdrInRoute = inRoute.getRugName(); // INROUTE OPERATOR
			String cdrInRouteDesc = inRoute.getEtgDesc();

			Route outRoute = mapRoute(item.getCdr_out_route(), cdrDate); // OUTROUTE
																			// OPERATOR
			String cdrOutRoute = outRoute.getRugName();
			String cdrOutRouteDesc = outRoute.getEtgDesc();

			List<String> Ano = mapNumber(item.getCdr_a_no(), cdrDate);
			List<String> Bno = mapNumber(item.getCdr_b_no(), cdrDate);

			String cdrANo = item.getCdr_a_no();
			String cdrANoGroup = Ano.get(1); // ANO GROUP NAME
			String cdrBNo = item.getCdr_b_no(); // BNO GROUP NAME
			String cdrBnoGroup = Bno.get(1);
			String cdrBearerService = mapService(item.getCdr_bearer_service());
			String cdrExtra9 = mapTrafficType(cdrInRouteDesc, cdrOutRouteDesc);
			String cdrExtra12 = Ano.get(0); // ANO OPERATOR NAME
			String cdrExtra13 = Bno.get(0); // BNO OPERATOR NAME
			String cdrExtra14 = item.getCdr_in_route();
			String cdrExtra15 = item.getCdr_out_route();
			String cdrExtra17 = item.getCdr_extra17();
			String cdrExtra18 = item.getCdr_duration();
			String cdr_mvno_name = map_mvno(cdrANoGroup, cdrInRouteDesc,
					cdrOutRouteDesc, cdrBnoGroup, Ano.get(0), Bno.get(0),
					item.getCdr_extra9(), item.getCdr_extra19());

			if (cdr_mvno_name != null) {

				cdr.setCdr_date(cdrDate);
				cdr.setCdr_time(cdrTime);
				cdr.setCdr_duration(cdrDuration);
				cdr.setCdr_in_route(cdrInRoute);
				cdr.setCdr_out_route(cdrOutRoute);
				cdr.setCdr_a_no(cdrANo);
				cdr.setCdr_b_no(cdrBNo);
				cdr.setCdr_bearer_service(cdrBearerService);
				cdr.setCdr_extra9(cdrExtra9);
				cdr.setCdr_extra10(" ");
				cdr.setCdr_extra11(" ");
				cdr.setCdr_extra12(cdrExtra12);
				cdr.setCdr_extra13(cdrExtra13);
				cdr.setCdr_extra14(cdrExtra14);
				cdr.setCdr_extra15(cdrExtra15);
				cdr.setCdr_extra16(" ");
				cdr.setCdr_extra17(cdrExtra17);
				cdr.setCdr_extra18(cdrExtra18);
				cdr.setCdr_extra19(" ");
				cdr.setCdr_mvno_name(cdr_mvno_name);
				log.debug("Write CDR " + cdr);
				return cdr;
			} else {
				log.debug("SKIP CDR CASE 1 " + cdr);
				return null;
			}
		} else {
			log.debug("SKIP CDR CASE 2 " + cdr);
			return null;
		}
	}

	private String map_mvno(final String cdrANoGroup,
			final String cdrInRouteDesc, final String cdrOutRouteDesc,
			final String cdrBnoGroup, final String AnoOper,
			final String BnoOper, final String source1, final String source2) {

		String mvno_name = null;

		if (cdrANoGroup.equalsIgnoreCase(Status.CAT_GROUP.toString())) {
			mvno_name = AnoOper.substring(0, 3);
		} else if (cdrBnoGroup.equalsIgnoreCase(Status.CAT_GROUP.toString())) {
			mvno_name = BnoOper.substring(0, 3);
		} else if (!cdrANoGroup.equalsIgnoreCase(Status.CAT_GROUP.toString())
				&& !cdrBnoGroup.equalsIgnoreCase(Status.CAT_GROUP.toString())) {
			if (source1.contains("RMV") || source2.contains("RMV")) {
				mvno_name = "RMV";
			} else
				mvno_name = "CASE1 cdrANoGroup=[" + cdrANoGroup
						+ "] cdrBnoGroup=[" + cdrBnoGroup + "] source1=["
						+ source1 + "] source2=[" + source2 + "]";

		} else {
			mvno_name = "CASE2 cdrANoGroup=[" + cdrANoGroup + "] cdrBnoGroup=["
					+ cdrBnoGroup + "] source1=[" + source1 + "] source2=["
					+ source2 + "]";
			;
		}
		log.debug("MVNO NAME: [" + mvno_name + "]");
		return mvno_name;
		// return cdrANoGroup + "->" + cdrInRouteDesc + "->" + cdrOutRouteDesc
		// + "->" + cdrBnoGroup;
	}

	private String mapTrafficType(final String inRouteDesc,
			final String outRouteDesc) {
		String type = null;

		if (inRouteDesc.equalsIgnoreCase(Status.CAT_GROUP.toString())
				&& (outRouteDesc.equalsIgnoreCase(Status.OPERATOR.toString()) || outRouteDesc
						.equalsIgnoreCase(Status.INTERNATIONAL.toString()))) {
			type = "OUT";
		} else if ((inRouteDesc.equalsIgnoreCase(Status.OPERATOR.toString()) || inRouteDesc
				.equalsIgnoreCase(Status.INTERNATIONAL.toString()))
				&& outRouteDesc.equalsIgnoreCase(Status.CAT_GROUP.toString())) {
			type = "IN";
		} else if (inRouteDesc.equalsIgnoreCase(Status.CAT_GROUP.toString())
				&& outRouteDesc.equalsIgnoreCase(Status.CAT_GROUP.toString())) {
			type = "INTERNAL";
		} else {
			type = Status.UNKNOWN.toString();
		}

		log.debug("mapTrafficType AnoGroup=" + inRouteDesc + " BnoGroup="
				+ outRouteDesc + " = [" + type + "]");
		return type;
	}

	private String mapService(final String cdr_bearer_service) {
		String service = null;
		if (cdr_bearer_service.contains("SMS")) {
			service = "SMS";
		} else if (cdr_bearer_service.contains("MMS")) {
			service = "MMS";
		} else
			service = "VOI";
		log.debug("ORIGINAL SERVICE=" + cdr_bearer_service + " NEW SERVICE="
				+ service);
		return service;
	}

	private List<String> mapNumber(final String number, final String cdrDate)
			throws DataAccessException, ParseException {
		log.debug("map full number =" + number);
		return elementService.getOnwerByAnyNumber(number, cdrDate);

	}

	private Route mapRoute(final String route, final String cdr_date)
			throws DataAccessException, ParseException {
		log.debug("map route =" + route);
		return routeService.getRouteInfoByRouteName(route, cdr_date);
	}

}
