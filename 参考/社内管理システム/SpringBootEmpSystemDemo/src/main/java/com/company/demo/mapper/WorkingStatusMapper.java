package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.company.demo.bean.RetirementPayAndHealthInsurance;
import com.company.demo.bean.WorkingStatus;

@Mapper
public interface WorkingStatusMapper {
	public List<WorkingStatus> searchWorkingStatusList();
	// search by empCd
	public List<WorkingStatus> search_empCd(@Param("keyword")String keyword);
	// delete by empCd
	public void WorkingStatus_delete(String empCd);
	// update by empCd
	public void WorkingStatus_update(WorkingStatus WorkingStatus);
	// details by empCd
	public WorkingStatus getWorkingStatusByEmpCd(String empCd);
	//add
	public void add(WorkingStatus WorkingStatus);


}
