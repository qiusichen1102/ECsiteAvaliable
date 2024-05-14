package com.example.demo.mapper.front;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RegisterMapper {
    //ユーザーの新規登録
    @Insert("Insert Into user(name,email,password,created_at,updated_at,delete_flg,version) VALUES (#{name},#{email},#{password},#{created_at},#{updated_at},#{delete_flg},#{version})")
    public void register(String name, String email, String password, LocalDateTime created_at, LocalDateTime updated_at, int delete_flg, int version);
    //すべてのメールアドレスを取得する
    @Select("Select email from user")
    public List<String> AllAdmin();
}
