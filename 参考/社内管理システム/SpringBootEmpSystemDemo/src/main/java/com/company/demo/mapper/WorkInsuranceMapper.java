package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.company.demo.bean.WorkInsurance;
import com.company.demo.bean.WorkingStatus;

@Mapper
public interface WorkInsuranceMapper {

	// list
	public List<WorkInsurance> searchWorkInsuranceList();
	// search by empCd
	public List<WorkInsurance> search_empCd(@Param("keyword")String keyword);
		// delete by empCd
	public void WorkInsurance_delete(String empCd);
	// update by empCd
	public void WorkInsurance_update(WorkInsurance WorkInsurance);
	// details by empCd
	public WorkInsurance getWorkInsuranceByEmpCd(String empCd);
	//add
	public void add(WorkInsurance WorkInsurance);

}
