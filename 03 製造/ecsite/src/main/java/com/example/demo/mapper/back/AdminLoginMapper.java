package com.example.demo.mapper.back;

import com.example.demo.bean.front.admin;
import com.example.demo.form.back.AdminLoginFrom;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminLoginMapper {
    // ログインリストを検索し、アカウントとパスワードを検証する
    @Select("select * from admin where email=#{email} and password=#{password}")
    public AdminLoginFrom Login(String email, String password);

    // remember_tokenを設定する
    @Update("UPDATE admin SET remember_token = #{remember_token},updated_at = #{updated_at},version = version + 1  WHERE id = #{id} AND version = #{current_version}")
    public int setRemember_token(String remember_token, int id,LocalDateTime updated_at,int current_version);
    
    //IDに基づいてユーザーのバージョンを検索する    
    @Select("SELECT version FROM admin WHERE id = #{id}")
    public int selectRemember_token(int id);
    
    // remember_tokenに基づいてユーザーを検索する
    @Select("select * from admin where remember_token=#{remember_token}")
    public AdminLoginFrom findUserByRemember_token(String remember_token);
    
    // Last_login_atを設定する
    @Update("UPDATE admin SET last_login_at = #{last_login_at},version = version + 1  WHERE email = #{email} AND password = #{password} AND version = #{current_version}")
    public int setAdminLast_login_at(String email, String password, LocalDateTime last_login_at,int current_version);
    
    //emailとパスワードに基づいてユーザーのバージョンを検索する    
    @Select("SELECT version FROM admin WHERE email = #{email} AND password = #{password}")
    public int selectAdminLast(String email, String password);
}
