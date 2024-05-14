package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	private String admin_id ;
	private String admin_name;
	private String admin_email_address;
	private String admin_password;
	

}
