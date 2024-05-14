package com.company.demo.bean;

import java.util.Date;

public class WorkInsurance {
	private String empCd;
	private String workInsuranceState;
	private String Salary;
	private Date applicationDate;
	private Date startingDate;
	private Date quitDate;
	private String workInsuranceNo;
	private String reason;
	private String workInsuranceComment;
	private String nameKanji;
	private String nameKana;

	private Integer delFlg;
	private Date insDate;
	private String insEmpCode;
	private Date updDate;
	private String updEmpCode;
	public WorkInsurance(){}
	public WorkInsurance(	String empCd,String workInsuranceState,String Salary,Date applicationDate,
			Date startingDate,Date quitDate,String workInsuranceNo,String reason,String workInsuranceComment) {
		this.empCd = empCd;
		this.workInsuranceState = workInsuranceState;
		this.Salary = Salary;
		this.applicationDate = applicationDate;
		this.startingDate = startingDate;
		this.quitDate = quitDate;
		this.workInsuranceNo = workInsuranceNo;
		this.reason = reason;
		this.workInsuranceComment = workInsuranceComment;

	}

	public String getNameKanji() {
		return nameKanji;
	}

	public void setNameKanji(String nameKanji) {
		this.nameKanji = nameKanji;
	}

	public String getNameKana() {
		return nameKana;
	}

	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}

	public String getEmpCd() {
		return empCd;
	}

	public void setEmpCd(String empCd) {
		this.empCd = empCd;
	}

	public String getWorkInsuranceState() {
		return workInsuranceState;
	}

	public void setWorkInsuranceState(String workInsuranceState) {
		this.workInsuranceState = workInsuranceState;
	}

	public String getSalary() {
		return Salary;
	}

	public void setSalary(String salary) {
		Salary = salary;
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

	public String getWorkInsuranceNo() {
		return workInsuranceNo;
	}

	public void setWorkInsuranceNo(String workInsuranceNo) {
		this.workInsuranceNo = workInsuranceNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getWorkInsuranceComment() {
		return workInsuranceComment;
	}

	public void setWorkInsuranceComment(String workInsuranceComment) {
		this.workInsuranceComment = workInsuranceComment;
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
		return "WorkInsurance [empCd=" + empCd + ", workInsuranceState=" + workInsuranceState + ", Salary=" + Salary
				+ ", applicationDate=" + applicationDate + ", startingDate=" + startingDate + ", quitDate=" + quitDate
				+ ", workInsuranceNo=" + workInsuranceNo + ", reason=" + reason + ", workInsuranceComment="
				+ workInsuranceComment + ", nameKanji=" + nameKanji + ", nameKana=" + nameKana + ", delFlg=" + delFlg
				+ ", insDate=" + insDate + ", insEmpCode=" + insEmpCode + ", updDate=" + updDate + ", updEmpCode="
				+ updEmpCode + "]";
	}




}
