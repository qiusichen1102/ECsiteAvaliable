package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.company.demo.bean.EmpInfo;
import com.company.demo.bean.HumanResource;

@Mapper
public interface EmpInfoMapper {

	// list
	public List<EmpInfo> searchEmpList();

	// list2
	public List<EmpInfo> getEmpInfo();



	// search by empCd
	public List<EmpInfo> search_empCd(@Param("keyword") String keyword);

	// delete by empCd
	public void delete(String empCd);

	// add
	public void add(EmpInfo empInfo);

	// update by empCd
	public void update(EmpInfo empInfo);

	// details by empCd
	public EmpInfo getEmpInfoByEmpCd(String empCd);


	// add empCd into the other tables
	public void addEngineerList(String empCd);

	public void addHumanResource(String empCd);

	public void addRetirementPayAndHealthInsurance(String empCd);

	public void addWorkingStatus(String empCd);

	public void addWorkInsurance(String empCd);





}
