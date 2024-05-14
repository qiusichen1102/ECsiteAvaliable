package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.company.demo.bean.EngineerList;

@Mapper
public interface EngineerListMapper {
	// list

	public List<EngineerList> searchEngineerListList();
	// list
	public List<EngineerList> search_empCd(@Param("keyword")String keyword,@Param("month")String month);
	public List<EngineerList> search_empCd1(@Param("keyword")String keyword);
	// delete by empCd
	public void engineerList_delete(String empCd);
	// details by empCd
	public EngineerList getEngineerListByEmpCd(String empCd);
	// update by empCd
	public void engineerList_update(EngineerList engineerList) ;
	@Select("select * from t_engineerList")
    public List<EngineerList> getEngineer();

	public void Engineer_import(EngineerList engineerList);

}
