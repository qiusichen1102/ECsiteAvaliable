package com.company.demo.bean;

import java.util.Date;

public class LoginUser {
	private String account_id;
	private String role_id;
	private String password;
	private Integer delFlg;
	private Date insDate;
	private String insEmpCode;
	private Date updDate;
	private String updEmpCode;
	
	public LoginUser(){}
	
	public LoginUser(String account_id, String role_id, String password) {
		// TODO Auto-generated constructor stub
	}

	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
