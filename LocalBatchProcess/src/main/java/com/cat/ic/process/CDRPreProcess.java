package com.cat.ic.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cat.ic.model.CDR;
import com.cat.ic.model.Status;
import com.cat.ic.service.IPrefixVTAService;

public class CDRPreProcess implements ItemProcessor<CDR, CDR> {
	private final Logger log = LoggerFactory.getLogger(CDRPreProcess.class);
	private IPrefixVTAService prefService;

	public void setPrefService(IPrefixVTAService prefService) {
		this.prefService = prefService;
	}

	public CDRPreProcess() {

	}

	@Override
	public CDR process(CDR item) throws Exception {
		log.debug(item.toString());
		CDR cdr = new CDR();
		String service = mapService(item.getCdr_b_no(),
				item.getCdr_bearer_service());
		String tfg = null;
		
		if (service.equalsIgnoreCase("VOI"))
			tfg = item.getCdr_extra9();
		else
			tfg = "ITSM|"+item.getCdr_extra9();

		String[] sn = service.split("\\|");
		log.debug("service = " + service);
		if (sn[0].equalsIgnoreCase(Status.UNKNOWN.toString())) {sn[0] = item.getCdr_bearer_service();}
		
		cdr.setCdr_date(item.getCdr_date() + " ");
		cdr.setCdr_time(item.getCdr_time() + " ");
		cdr.setCdr_duration(item.getCdr_duration() + " ");
		cdr.setCdr_in_route(item.getCdr_in_route() + " ");
		cdr.setCdr_out_route(item.getCdr_out_route() + " ");
		cdr.setCdr_a_no(item.getCdr_a_no() + " ");
		cdr.setCdr_b_no(sn[1] + " ");			// remove prefix
		cdr.setCdr_bearer_service(sn[0] +" ");
		cdr.setCdr_extra9(tfg + " ");
		cdr.setCdr_extra10(item.getCdr_extra10() + " ");
		cdr.setCdr_extra11(item.getCdr_extra11() + " ");
		cdr.setCdr_extra12(item.getCdr_a_no()+"|"+item.getCdr_extra12() + " ");	//append original a_no
		cdr.setCdr_extra13(item.getCdr_b_no()+"|"+item.getCdr_extra13() + " ");	//append original b_no
		cdr.setCdr_extra14(item.getCdr_extra14() + " ");
		cdr.setCdr_extra15(item.getCdr_extra15() + " ");
		cdr.setCdr_extra16(item.getCdr_extra16() + " ");
		cdr.setCdr_extra17(item.getCdr_extra17() + " ");
		cdr.setCdr_extra18(item.getCdr_extra18() + " ");
		cdr.setCdr_extra19(item.getCdr_extra19() + " ");

		return cdr;
	}

	private String mapService(String cdr_b_no, String orgService) {
		if (!cdr_b_no.startsWith("66")) {
			String service = prefService.getService(cdr_b_no);
			if (!(service == null)) {
				return service;
			} else {
				return orgService+"|"+cdr_b_no;
			}
		} else {
			return orgService+"|"+cdr_b_no;
		}
	}
}
