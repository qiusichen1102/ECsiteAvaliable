package com.example.demo.service.back;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.front.User;
import com.example.demo.mapper.back.AdminUserMapper;

@Service
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Override
	// delete_flgが0のすべてのユーザー情報を取得します。
	public List<User> selectUser() {
		return adminUserMapper.selectUser();
	}
	@Override
	// 指定したIDに対応するユーザー情報を取得します。
	public User selectUserByID(int id) {
		return adminUserMapper.selectUserByID(id);
	}
	@Override
	// 指定したIDに対応するユーザー情報の名前とメールアドレスを更新します。
	public int updateUser(String name, String email, int id, LocalDateTime updateTime,int current_version) {
		return adminUserMapper.updateUser(name,email,id,updateTime,current_version);
	}
	@Override
	// 指定したIDに対応するユーザー情報を削除します。
	public int deleteUser(int id, LocalDateTime updateTime) {
		return adminUserMapper.deleteUser(id,updateTime);
	}
	@Override
	// 指定した名前に一致するユーザー情報を検索します。
	public List<User> selectUserByName(String name) {
		return adminUserMapper.selectUserByName(name);
	}
	@Override
	// 指定したメールアドレスに一致するユーザー情報を検索します。
	public List<User> selectUserByEmail(String email) {
		return adminUserMapper.selectUserByEmail(email);
	}

	@Override
	// 指定した名前とメールアドレスに一致するユーザー情報を検索します。
	public List<User> selectUserByAll(String name, String email) {
		return adminUserMapper.selectUserByAll(name,email);
	}
	
	@Override
	// 指定した名前versionを検索します。
	public int selectUpdateUserVersion(int id) {
		return adminUserMapper.selectUpdateUserVersion(id);
	}

}
