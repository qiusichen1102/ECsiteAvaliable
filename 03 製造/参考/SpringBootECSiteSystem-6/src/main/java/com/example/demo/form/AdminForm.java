package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class AdminForm {
	@NotEmpty(message ="{admin.error.admin_id.notEmpty}")
	private String admin_id ;
	@NotEmpty(message ="{admin.error.admin_name.notEmpty}")
	private String admin_name;
	@NotEmpty(message ="{admin.error.admin_email.notEmpty}")
	@Email(message ="{admin.error.admin_email_address.Email}")
	private String admin_email_address;
	@NotEmpty(message ="{admin.error.admin_password.notEmpty}")
	private String admin_password;
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getAdmin_email_address() {
		return admin_email_address;
	}
	public void setAdmin_email_address(String admin_email_address) {
		this.admin_email_address = admin_email_address;
	}
	public String getAdmin_password() {
		return admin_password;
	}
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}
	public AdminForm(String admin_id, String admin_name, String admin_email_address, String admin_password) {
		super();
		this.admin_id = admin_id;
		this.admin_name = admin_name;
		this.admin_email_address = admin_email_address;
		this.admin_password = admin_password;
	}
	
	public AdminForm() {
		
	}
}
