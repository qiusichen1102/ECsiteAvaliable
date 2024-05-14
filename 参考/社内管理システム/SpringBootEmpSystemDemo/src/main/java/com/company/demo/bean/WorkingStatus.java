package com.company.demo.bean;

import java.util.Date;

public class WorkingStatus {

	  private String empCd; // 社員番号',
	  private String  projectName; //案件名',
	  private String projectAddress;//勤務地
	  private String projectStation;//最寄り駅',
	  private String transportationFee;//交通費',
	  private Date startingDay;//開始日',
	  private Date endingDay;//終了予定日',
	  private String maxWorkTime;//上限作業時間',
	  private String minWorkTime;//下限作業時間',
	  private String workTime;//作業時間',
	  private String actuarial;//精算有無',
	  private String requestAmount;//請求金額',
	  private String contractCompanyAddress;//契約先',
	  private String contractCompanyName;//契約会社名',
	  private String projectDepartment;//業務担当部署',
	  private String projecetLeader;//連絡先担当者',
	  private String projectTelNo;//連絡先番号',
	  private String projectEmail;//連絡先メールアドレス',
	  private String contractType;//契約条件',
	  private String paymentTerm;//支払条件',
	  private String companyLeader;//本社担当者',
	  private String workingStatusComment;//備考',
	  private String nameKanji;
	  private String sex;
	  private String company;
	  private String telNo;

	  private Integer delFlg;
	  private Date insDate;
	  private String insEmpCode;
	  private Date updDate;
	  private String updEmpCode;

	  public WorkingStatus(){}
	public WorkingStatus(String empCd,String projectName,String projectAddress,String projectStation,String transportationFee,
			Date startingDay,Date endingDay,String maxWorkTime,String minWorkTime,String workTime,String actuarial,String requestAmount,
			String contractCompanyAddress,String contractCompanyName,String projectDepartment,String projecetLeader,
			String projectTelNo,String projectEmail,String contractType,String paymentTerm,String companyLeader,String workingStatusComment
			) {
		  this.empCd=empCd;
		  this.projectName=projectName;
		  this.projectAddress=projectAddress;
		  this.projectStation=projectStation;
		  this.transportationFee=transportationFee;
		  this.startingDay=startingDay;
		  this.endingDay=endingDay;
		  this.maxWorkTime=maxWorkTime;
		  this.minWorkTime=minWorkTime;
		  this.workTime=workTime;
		  this.actuarial=actuarial;
		  this.requestAmount=requestAmount;
		  this.contractCompanyAddress=contractCompanyAddress;
		  this.contractCompanyName=contractCompanyName;
		  this.projectDepartment=projectDepartment;
		  this.projecetLeader=projecetLeader;
		  this.projectTelNo=projectTelNo;
		  this.projectEmail=projectEmail;
		  this.contractType=contractType;
		  this.paymentTerm=paymentTerm;
		  this.companyLeader=companyLeader;
		  this.workingStatusComment=workingStatusComment;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getNameKanji() {
		return nameKanji;
	}
	public void setNameKanji(String nameKanji) {
		this.nameKanji = nameKanji;
	}
	public String getEmpCd() {
		return empCd;
	}
	public void setEmpCd(String empCd) {
		this.empCd = empCd;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectAddress() {
		return projectAddress;
	}
	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	public Date getStartingDay() {
		return startingDay;
	}
	public void setStartingDay(Date startingDay) {
		this.startingDay = startingDay;
	}
	public String getProjectStation() {
		return projectStation;
	}
	public void setProjectStation(String projectStation) {
		this.projectStation = projectStation;
	}
	public String getTransportationFee() {
		return transportationFee;
	}
	public void setTransportationFee(String transportationFee) {
		this.transportationFee = transportationFee;
	}
	public Date getEndingDay() {
		return endingDay;
	}
	public void setEndingDay(Date endingDay) {
		this.endingDay = endingDay;
	}
	public String getMaxWorkTime() {
		return maxWorkTime;
	}
	public void setMaxWorkTime(String maxWorkTime) {
		this.maxWorkTime = maxWorkTime;
	}
	public String getMinWorkTime() {
		return minWorkTime;
	}
	public void setMinWorkTime(String minWorkTime) {
		this.minWorkTime = minWorkTime;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getActuarial() {
		return actuarial;
	}
	public void setActuarial(String actuarial) {
		this.actuarial = actuarial;
	}
	public String getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}
	public String getContractCompanyAddress() {
		return contractCompanyAddress;
	}
	public void setContractCompanyAddress(String contractCompanyAddress) {
		this.contractCompanyAddress = contractCompanyAddress;
	}
	public String getContractCompanyName() {
		return contractCompanyName;
	}
	public void setContractCompanyName(String contractCompanyName) {
		this.contractCompanyName = contractCompanyName;
	}
	public String getProjectDepartment() {
		return projectDepartment;
	}
	public void setProjectDepartment(String projectDepartment) {
		this.projectDepartment = projectDepartment;
	}
	public String getProjecetLeader() {
		return projecetLeader;
	}
	public void setProjecetLeader(String projecetLeader) {
		this.projecetLeader = projecetLeader;
	}
	public String getProjectTelNo() {
		return projectTelNo;
	}
	public void setProjectTelNo(String projectTelNo) {
		this.projectTelNo = projectTelNo;
	}
	public String getProjectEmail() {
		return projectEmail;
	}
	public void setProjectEmail(String projectEmail) {
		this.projectEmail = projectEmail;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public String getCompanyLeader() {
		return companyLeader;
	}
	public void setCompanyLeader(String companyLeader) {
		this.companyLeader = companyLeader;
	}
	public String getWorkingStatusComment() {
		return workingStatusComment;
	}
	public void setWorkingStatusComment(String workingStatusComment) {
		this.workingStatusComment = workingStatusComment;
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



}
