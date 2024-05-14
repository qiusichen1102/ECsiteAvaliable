package com.example.demo.service.back;

import java.time.LocalDateTime;

import com.example.demo.bean.front.admin;
import com.example.demo.form.back.AdminLoginFrom;

public interface AdminLoginService {
    public AdminLoginFrom AdminLogin(String email,String password);

    public int setRemember_token(String remember_token ,int id,LocalDateTime updated_at,int current_version);

    public AdminLoginFrom findUserByRemember_token(String remember_token);
    
    public int setAdminLast_login_at(String email, String password, LocalDateTime last_login_at,int current_version);
    
    public int selectRemember_token(int current_version);
    
    public int selectAdminLast(String email, String password);
}
