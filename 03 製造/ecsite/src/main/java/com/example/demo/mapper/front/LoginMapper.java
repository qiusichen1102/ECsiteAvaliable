package com.example.demo.mapper.front;

import com.example.demo.bean.front.User;
import com.example.demo.bean.front.admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface LoginMapper {
	// ログイン情報を検証します。
	@Select("Select * from user where email = #{email} and password = #{password}")
	public User Login(String email, String password);

	// remember_tokenを設定します。
	@Update("UPDATE user SET remember_token = #{remember_token} WHERE id = #{id}")
	public int setRemember_token(String remember_token, int id);

	// remember_tokenに基づいてユーザーを検索します。
	@Select("select * from user where remember_token=#{remember_token}")
	public User findUserByRemember_token(String remember_token);

	//最終ログイン時間を更新する
	@Update("UPDATE user SET last_login_at = #{last_login_at} where email = #{email} and password = #{password}")
	public int setLast_login_at(String email, String password, LocalDateTime last_login_at);
}
