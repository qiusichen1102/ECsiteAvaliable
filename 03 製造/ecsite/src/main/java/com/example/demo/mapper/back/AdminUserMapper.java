package com.example.demo.mapper.back;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.bean.front.User;
import com.example.demo.form.back.AdminContactForm;

@Mapper
public interface AdminUserMapper {
	// delete_flgが0のすべてのユーザー情報を取得します。
	@Select("SELECT * FROM user WHERE delete_flg = 0;")
	public List<User> selectUser();

	// 指定したIDに対応するユーザー情報を取得します。
	@Select("SELECT * FROM user WHERE id = #{id} AND delete_flg = 0")
	public User selectUserByID(int id);

	// 指定した名前に一致するユーザー情報を検索します。
	@Select("SELECT * FROM user WHERE name =#{name} AND delete_flg = 0")
	public List<User> selectUserByName(String name);

	// 指定したメールアドレスに一致するユーザー情報を検索します。
	@Select("SELECT * FROM user WHERE email =#{email} AND delete_flg = 0")
	public List<User> selectUserByEmail(String email);

	// 指定した名前とメールアドレスに一致するユーザー情報を検索します。
	@Select("SELECT * FROM user WHERE name =#{name} AND email =#{email} AND delete_flg = 0")
	public List<User> selectUserByAll(String name, String email);

	// 指定したIDに対応するユーザー情報の名前とメールアドレスを更新します。
	@Update("UPDATE user SET name = #{name},email = #{email} ,updated_at=#{updated_at},version = version + 1 WHERE id = #{id} AND delete_flg = 0 AND version = #{current_version}")
	public int updateUser(String name, String email, int id, LocalDateTime updated_at,int current_version);
	
	// 指定した名前versionを検索します。
	@Select("SELECT version FROM user WHERE id = #{id}")
	public int selectUpdateUserVersion(int id);
	
	// 指定したIDに対応するユーザー情報を削除します。
	@Update("UPDATE user SET delete_flg = 1 ,updated_at=#{updated_at}  WHERE id = #{id}")
	public int deleteUser(int id, LocalDateTime updated_at);

}
