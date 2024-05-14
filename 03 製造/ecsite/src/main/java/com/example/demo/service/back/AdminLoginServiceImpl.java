package com.example.demo.service.back;

import com.example.demo.bean.front.admin;
import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.mapper.back.AdminLoginMapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginServiceImpl implements AdminLoginService{
    @Autowired
    private AdminLoginMapper adminLoginMapper;
    // ログインリストを検索し、アカウントとパスワードを検証する
    @Override
    public AdminLoginFrom AdminLogin(String email, String password) {
        return adminLoginMapper.Login(email,password);
    }

    // remember_tokenを設定する
    @Override
    public int setRemember_token(String remember_token, int id,LocalDateTime updated_at,int current_version) {
        return adminLoginMapper.setRemember_token(remember_token,id,updated_at,current_version);
    }
    
    // remember_tokenに基づいてユーザーを検索する
    @Override
    public AdminLoginFrom findUserByRemember_token(String remember_token) {
        return adminLoginMapper.findUserByRemember_token(remember_token);
    }
    
    // Last_login_atを設定する
    @Override
    public int setAdminLast_login_at(String email, String password, LocalDateTime last_login_at,int current_version) {
    	return adminLoginMapper.setAdminLast_login_at(email, password, last_login_at,current_version);
    }

    //IDに基づいてユーザーのバージョンを検索する
	@Override
	public int selectRemember_token(int id) {
		return adminLoginMapper.selectRemember_token(id);
	}

    //emailとパスワードに基づいてユーザーのバージョンを検索する	
	@Override
	public int selectAdminLast(String email, String password) {
		return adminLoginMapper.selectAdminLast(email, password);
	}
    
}
