package com.company.demo.bean;

import java.util.Date;

public class HumanResource {
	private String empCd;
	private String nameKanji;
	private String nameKana;
	private String sex;
	private Date birthday;
	private String employeeType;
	private String postcode;
	private String address;
	private String telNo;
	private String email;
	private String companyEmail;
	private Date enteringDate;
	private Date dimissionDate;
	private String homeStation;
	private String basicSalary;
	private String salaryTimes;
	private String insuranceLine;
	private String healthInsurance;
	private String retirementPay;
	private String diseaseInturance;
	private String humanResourceComment;

	private Integer delFlg;
	private Date insDate;
	private String insEmpCode;
	private Date updDate;
	private String updEmpCode;

	public HumanResource() {}
	public HumanResource(String empCd,String basicSalary,String salaryTimes,String insuranceLine,String healthInsurance,String retirementPay,
			String diseaseInturance,String humanResourceComment) {
		this.empCd = empCd;
		this.basicSalary = basicSalary;
		this.salaryTimes = salaryTimes;
		this.insuranceLine = insuranceLine;
		this.healthInsurance = healthInsurance;
		this.retirementPay = retirementPay;
		this.diseaseInturance = diseaseInturance;
		this.humanResourceComment = humanResourceComment;
	}
	public String getEmpCd() {
		return empCd;
	}
	public void setEmpCd(String empCd) {
		this.empCd = empCd;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public Date getEnteringDate() {
		return enteringDate;
	}
	public void setEnteringDate(Date enteringDate) {
		this.enteringDate = enteringDate;
	}
	public Date getDimissionDate() {
		return dimissionDate;
	}
	public void setDimissionDate(Date dimissionDate) {
		this.dimissionDate = dimissionDate;
	}
	public String getHomeStation() {
		return homeStation;
	}
	public void setHomeStation(String homeStation) {
		this.homeStation = homeStation;
	}
	public String getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(String basicSalary) {
		this.basicSalary = basicSalary;
	}
	public String getSalaryTimes() {
		return salaryTimes;
	}
	public void setSalaryTimes(String salaryTimes) {
		this.salaryTimes = salaryTimes;
	}
	public String getInsuranceLine() {
		return insuranceLine;
	}
	public void setInsuranceLine(String insuranceLine) {
		this.insuranceLine = insuranceLine;
	}
	public String getHealthInsurance() {
		return healthInsurance;
	}
	public void setHealthInsurance(String healthInsurance) {
		this.healthInsurance = healthInsurance;
	}
	public String getRetirementPay() {
		return retirementPay;
	}
	public void setRetirementPay(String retirementPay) {
		this.retirementPay = retirementPay;
	}
	public String getDiseaseInturance() {
		return diseaseInturance;
	}
	public void setDiseaseInturance(String diseaseInturance) {
		this.diseaseInturance = diseaseInturance;
	}
	public String getHumanResourceComment() {
		return humanResourceComment;
	}
	public void setHumanResourceComment(String humanResourceComment) {
		this.humanResourceComment = humanResourceComment;
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
