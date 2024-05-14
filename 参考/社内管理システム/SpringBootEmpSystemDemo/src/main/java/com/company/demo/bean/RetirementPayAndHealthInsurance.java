package com.company.demo.bean;

import java.util.Date;

public class RetirementPayAndHealthInsurance {
	private String empCd;
	private String state;
	private String insuranceLine;
	private Date applicationDate;
	private Date startingDate;
	private Date quitDate;
	private String InturanceNote;
	private String HealthInsurance;
	private String retirementPayAndHealthInsuranceComment;
	private Integer delFlg;
	private Date insDate;
	private String insEmpCode;
	private Date updDate;
	private String updEmpCode;
	public RetirementPayAndHealthInsurance() {
	}

	public RetirementPayAndHealthInsurance(String empCd, String state, String insuranceLine, Date applicationDate,
			Date startingDate, Date quitDate, String InturanceNote, String HealthInsurance,
			String retirementPayAndHealthInsuranceComment) {
	}

	public String getEmpCd() {
		return empCd;
	}

	public void setEmpCd(String empCd) {
		this.empCd = empCd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInsuranceLine() {
		return insuranceLine;
	}

	public void setInsuranceLine(String insuranceLine) {
		this.insuranceLine = insuranceLine;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(Date quitDate) {
		this.quitDate = quitDate;
	}

	public String getInturanceNote() {
		return InturanceNote;
	}

	public void setInturanceNote(String inturanceNote) {
		InturanceNote = inturanceNote;
	}

	public String getHealthInsurance() {
		return HealthInsurance;
	}

	public void setHealthInsurance(String healthInsurance) {
		HealthInsurance = healthInsurance;
	}

	public String getRetirementPayAndHealthInsuranceComment() {
		return retirementPayAndHealthInsuranceComment;
	}

	public void setRetirementPayAndHealthInsuranceComment(String retirementPayAndHealthInsuranceComment) {
		this.retirementPayAndHealthInsuranceComment = retirementPayAndHealthInsuranceComment;
	}

	public Integer getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getInsEmpCode() {
		return insEmpCode;
	}

	public void setInsEmpCode(String insEmpCode) {
		this.insEmpCode = insEmpCode;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdEmpCode() {
		return updEmpCode;
	}

	public void setUpdEmpCode(String updEmpCode) {
		this.updEmpCode = updEmpCode;
	}

	@Override
	public String toString() {
		return "RetirementPayAndHealthInsurance [empCd=" + empCd + ", state=" + state + ", insuranceLine="
				+ insuranceLine + ", applicationDate=" + applicationDate + ", startingDate=" + startingDate
				+ ", quitDate=" + quitDate + ", InturanceNote=" + InturanceNote + ", HealthInsurance=" + HealthInsurance
				+ ", retirementPayAndHealthInsuranceComment=" + retirementPayAndHealthInsuranceComment + ", delFlg="
				+ delFlg + ", insDate=" + insDate + ", insEmpCode=" + insEmpCode + ", updDate=" + updDate
				+ ", updEmpCode=" + updEmpCode + "]";
	}


}
