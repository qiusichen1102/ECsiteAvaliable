package com.example.demo.service.front;

import com.example.demo.bean.front.User;
import com.example.demo.form.front.LoginForm;

import java.time.LocalDateTime;

public interface LoginService {
    public User Login(String email,String password);

    public int setRemember_token(String remember_token ,int id);

    public User findUserByRemember_token(String remember_token);

    public int setLast_login_at(String email, String password, LocalDateTime last_login_at);
}
