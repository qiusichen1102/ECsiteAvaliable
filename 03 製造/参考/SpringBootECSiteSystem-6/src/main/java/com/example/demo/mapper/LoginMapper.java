package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bean.Login;

@Mapper
public interface LoginMapper {
	public Login queryAdmin(String admin_email_address) ;
}
