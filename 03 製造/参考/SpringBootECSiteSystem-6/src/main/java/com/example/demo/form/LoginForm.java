package com.example.demo.form;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	@NotEmpty(message = "メールアドレスを入力してください!")
	@Email(message = "正しいメールアドレスを入力してください")
	private String admin_email_address;
	
	@Size(min = 6, max = 6,message = "パスワード欄に六桁半角英数字を入力してください")
	@NotEmpty(message = "パスワードを入力してください!")
	private String admin_password;


	
}