package com.cat.ic.model;

public class Route implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String rutCode;
	String rugName;
	String etgDesc;
	String rutFromDttm;
	String rutToDttm;
	
	
	public Route() {

	}


	public String getRutCode() {
		return rutCode;
	}


	public void setRutCode(String rutCode) {
		this.rutCode = rutCode;
	}


	public String getRugName() {
		return rugName;
	}


	public void setRugName(String rugName) {
		this.rugName = rugName;
	}


	public String getEtgDesc() {
		return etgDesc;
	}


	public void setEtgDesc(String etgDesc) {
		this.etgDesc = etgDesc;
	}


	public String getRutFromDttm() {
		return rutFromDttm;
	}


	public void setRutFromDttm(String rutFromDttm) {
		this.rutFromDttm = rutFromDttm;
	}


	public String getRutToDttm() {
		return rutToDttm;
	}


	public void setRutToDttm(String rutToDttm) {
		this.rutToDttm = rutToDttm;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Route [rutCode=" + rutCode + ", rugName=" + rugName
				+ ", etgDesc=" + etgDesc + ", rutFromDttm=" + rutFromDttm
				+ ", rutToDttm=" + rutToDttm + "]";
	}
	
}
