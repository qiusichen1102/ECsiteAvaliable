package com.example.demo.bean;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	private String admin_id ;
	private String admin_name;
	@NotNull(message = "メールアドレスを入力してください")
	private String admin_email_address;
	@NotNull(message = "パスワードを入力してください")
	private String admin_password;

}