package com.example.demo.service.back;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.bean.front.User;

public interface AdminUserService {
	public List<User> selectUser();
	public User selectUserByID(int id);
	public int updateUser(String name, String email, int id, LocalDateTime updateTime,int current_version);
	public int selectUpdateUserVersion(int id);
	public int deleteUser(int id, LocalDateTime updateTime);
	public List<User> selectUserByName(String name);
	public List<User> selectUserByEmail(String email);
	public List<User> selectUserByAll(String name,String email);
}
