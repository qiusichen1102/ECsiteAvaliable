package com.company.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.demo.bean.EmpInfo;
import com.company.demo.bean.LoginUser;
import com.company.demo.mapper.LoginMapper;

@Service
public class LoginService {
	@Autowired
	LoginMapper loginMapper;
	
	public List<LoginUser> getList() {
		List<LoginUser> loginList = loginMapper.getList();
		return loginList;
	}

	public LoginUser getInfoByAccount_id(String account_id) {
		LoginUser login = loginMapper.getInfoByAccount_id(account_id);
		return login;
	}

	public void addLogin(LoginUser login) {
		loginMapper.addLogin(login);
		
	}

	public void loginDelete(String account_id) {
		loginMapper.loginDelete(account_id);
		
	}

	public void updateLogin(LoginUser loginUser) {
		loginMapper.updateLogin(loginUser);
		
	}

	
	
	

}
