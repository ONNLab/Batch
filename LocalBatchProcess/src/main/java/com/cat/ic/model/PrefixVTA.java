package com.cat.ic.model;

public class PrefixVTA implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String prefix;
	String bearerService;
	
	public PrefixVTA() {
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getBearerService() {
		return bearerService;
	}

	public void setBearerService(String bearerService) {
		this.bearerService = bearerService;
	}

	@Override
	public String toString() {
		return "PrefixVTA [prefix=" + prefix + ", bearerService="
				+ bearerService + "]";
	}
	
}
