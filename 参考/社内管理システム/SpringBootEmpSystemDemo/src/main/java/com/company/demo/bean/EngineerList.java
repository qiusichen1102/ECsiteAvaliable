package com.company.demo.bean;

import java.util.Date;

public class EngineerList {
	private String empCd;
	private String nameKanji;
	private String sex;
	private String homeStation;
	private Date workingDate;

	private String experience;
	private String japanese;
	private String techlanguage;
	private String basicDesign;
	private String detailDesign;

	private String produce;
	private String test;
	private String project;
	private String level;

	private String newEmployee;
	private String engineerListComment;

	private Integer delFlg;
	private Date insDate;
	private String insEmpCode;
	private Date updDate;
	private String updEmpCode;

	public EngineerList() {}

	public EngineerList(String empCd, Date workingDate,	String experience, String japanese, String techlanguage,
			String basicDesign, String detailDesign,String produce, String test, String project, String level,
			String newEmployee, String engineerListComment) {

		this.empCd = empCd;
		this.workingDate = workingDate;
		this.experience = experience;
		this.japanese = japanese;
		this.techlanguage = techlanguage;
		this.basicDesign = basicDesign;
		this.detailDesign = detailDesign;
		this.produce = produce;
		this.test = test;
		this.project = project;
		this.level = level;
		this.newEmployee = newEmployee;
		this.engineerListComment = engineerListComment;
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
	public String getSex() {
		return sex;
	}
	public Date getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHomeStation() {
		return homeStation;
	}
	public void setHomeStation(String homeStation) {
		this.homeStation = homeStation;
	}

	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getJapanese() {
		return japanese;
	}
	public void setJapanese(String japanese) {
		this.japanese = japanese;
	}
	public String getTechlanguage() {
		return techlanguage;
	}
	public void setTechlanguage(String techlanguage) {
		this.techlanguage = techlanguage;
	}
	public String getBasicDesign() {
		return basicDesign;
	}
	public void setBasicDesign(String basicDesign) {
		this.basicDesign = basicDesign;
	}
	public String getDetailDesign() {
		return detailDesign;
	}
	public void setDetailDesign(String detailDesign) {
		this.detailDesign = detailDesign;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}

	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	public String getNewEmployee() {
		return newEmployee;
	}
	public void setNewEmployee(String newEmployee) {
		this.newEmployee = newEmployee;
	}
	public String getEngineerListComment() {
		return engineerListComment;
	}
	public void setEngineerListComment(String engineerListComment) {
		this.engineerListComment = engineerListComment;
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
