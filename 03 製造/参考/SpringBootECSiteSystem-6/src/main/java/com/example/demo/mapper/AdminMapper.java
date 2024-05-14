package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.bean.Admin;
import com.example.demo.form.AdminForm;
@Mapper
public interface AdminMapper {
	// list
	public List<Admin> searchAdminList();

	// delete by adminCd
	public void Admin_delete(String admin_id);

	//search by adminCd
	public List<Admin> Admin_search(@Param("keyword") String keyword);

	// details by adminCd
	public Admin Admin_searchbyadmin_id(String admin_id);

	// update by adminCd
	public void Admin_update(AdminForm adminForm);

	// insert
	public void Admin_insert(AdminForm adminForm);
}
