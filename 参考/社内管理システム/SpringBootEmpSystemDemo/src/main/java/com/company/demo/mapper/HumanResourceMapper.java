package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.company.demo.bean.HumanResource;
import com.company.demo.bean.RetirementPayAndHealthInsurance;

@Mapper
public interface HumanResourceMapper {
	// list
	public List<HumanResource> searchHumanResourceList();
	// list2
	public List<HumanResource> getHumanResource();


	// search by empCd
	public List<HumanResource> search_empCd(@Param("keyword")String keyword);
		// delete by empCd
	public void HumanResource_delete(String empCd);
	// update by empCd
	public void HumanResource_update(HumanResource HumanResource);
	// details by empCd
	public HumanResource getHumanResourceByEmpCd(String empCd);

	// add
	public void add(HumanResource HumanResource);
}
