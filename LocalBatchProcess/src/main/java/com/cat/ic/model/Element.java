package com.cat.ic.model;

public class Element implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String eltName;
	String eltDigits;
	String eltFromDttm;
	String eltToDttm;
	String eltDesc;

	public Element() {

	}


	public Element(String eltName, String eltDigits, String eltFromDttm,
			String eltToDttm) {
		super();
		this.eltName = eltName;
		this.eltDigits = eltDigits;
		this.eltFromDttm = eltFromDttm;
		this.eltToDttm = eltToDttm;
	}


	public String getEltName() {
		return eltName;
	}


	public void setEltName(String eltName) {
		this.eltName = eltName;
	}


	public String getEltDigits() {
		return eltDigits;
	}


	public void setEltDigits(String eltDigits) {
		this.eltDigits = eltDigits;
	}


	public String getEltFromDttm() {
		return eltFromDttm;
	}


	public void setEltFromDttm(String eltFromDttm) {
		this.eltFromDttm = eltFromDttm;
	}


	public String getEltToDttm() {
		return eltToDttm;
	}


	public void setEltToDttm(String eltToDttm) {
		this.eltToDttm = eltToDttm;
	}


	public String getEltDesc() {
		return eltDesc;
	}


	public void setEltDesc(String eltDesc) {
		this.eltDesc = eltDesc;
	}


	@Override
	public String toString() {
		return "Element [eltName=" + eltName + ", eltDigits=" + eltDigits
				+ ", eltFromDttm=" + eltFromDttm + ", eltToDttm=" + eltToDttm
				+ ", eltDesc=" + eltDesc + "]";
	}

	
	
	
}
