package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.demo.bean.LoginUser;

@Mapper
public interface LoginMapper {

	public List<LoginUser> getList() ;

	public LoginUser getInfoByAccount_id(String account_id);

	public void addLogin(LoginUser login);

	public void loginDelete(String account_id);

	public void updateLogin(LoginUser loginUser);

}
