package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.demo.bean.Login;
import com.example.demo.form.LoginForm;
import com.example.demo.mapper.LoginMapper;
@Service
public class LoginService {
	@Resource
	private LoginMapper loginMapper;

	@Autowired
	private MessageSource messageSource;


	public List<String> getResult(LoginForm loginform) {
		
		Login admin = loginMapper.queryAdmin(loginform.getAdmin_email_address());
		List<String> errorlist = new ArrayList<String>();
		if (admin == null) {
			errorlist.add("不正なようメールアドレス！");
		} else if (!admin.getAdmin_password().equals(loginform.getAdmin_password())) {
			errorlist.add("不正なようパスワード！");
		}
		return errorlist;
	}


}
