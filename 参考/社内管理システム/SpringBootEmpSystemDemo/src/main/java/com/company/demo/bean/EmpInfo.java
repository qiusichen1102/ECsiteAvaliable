package com.company.demo.bean;

import java.util.Date;

public class EmpInfo {
	private String empCd;
	private String email;
	private String companyEmail;
	private String nameKanji;
	private String nameKana;

	private String sex;
	private Date birthday;
	private String country;
	private String postcode;
	private String address;

	private String telNo;
	private String homeStation;
	private Date enteringDate;
	private Date dimissionDate;
	private String company;

	private String employeeType;
	private String comment;

	private Integer delFlg;
	private Date insDate;
	private String insEmpCode;
	private Date updDate;
	private String updEmpCode;

	public EmpInfo(){}
	public EmpInfo(	String empCd,String email,String companyEmail,String nameKanji,String nameKana,
			String sex,Date birthday,String country,String postcode,String address,String telNo,
			String homeStation,Date enteringDate,Date dimissionDate,String company,String employeeType,
			String comment) {
		this.empCd = empCd;
		this.email = email;
		this.companyEmail = companyEmail;
		this.nameKanji = nameKanji;
		this.nameKana = nameKana;
		this.sex = sex;
		this.birthday = birthday;
		this.country = country;
		this.postcode = postcode;
		this.address = address;
		this.telNo = telNo;
		this.homeStation = homeStation;
		this.enteringDate = enteringDate;
		this.dimissionDate = dimissionDate;
		this.company = company;
		this.employeeType = employeeType;
		this.comment = comment;
	}

	public String getEmpCd() {
		return empCd;
	}

	public void setEmpCd(String empCd) {
		this.empCd = empCd;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getHomeStation() {
		return homeStation;
	}

	public void setHomeStation(String homeStation) {
		this.homeStation = homeStation;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
