package com.example.demo.service.front;

import com.example.demo.bean.front.User;
import com.example.demo.form.front.LoginForm;
import com.example.demo.mapper.front.LoginMapper;
import com.example.demo.service.front.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
 
    @Override
	// ログイン情報を検証します。
    public User Login(String email,String password){
        return loginMapper.Login(email, password);
    }

    @Override
 // remember_tokenを設定します。
    public int setRemember_token(String remember_token, int id) {
        return loginMapper.setRemember_token(remember_token,id);
    }

    @Override
 // remember_tokenに基づいてユーザーを検索します。
    public User findUserByRemember_token(String remember_token) {
        return loginMapper.findUserByRemember_token(remember_token);
    }

    @Override
	//最終ログイン時間を更新する
    public int setLast_login_at(String email, String password, LocalDateTime last_login_at) {
        return loginMapper.setLast_login_at(email,password,last_login_at);
    }
}
