package com.company.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.company.demo.bean.RetirementPayAndHealthInsurance;

@Mapper
public interface RetirementPayAndHealthInsuranceMapper {

	// list
	List<RetirementPayAndHealthInsurance> getRetirementPayAndHealthInsurance();

	// search by empCd
	List<RetirementPayAndHealthInsurance> search_empCd(@Param("keyword") String keyword);

	// delete by empCd
	public void delete(String empCd);

	// add
	public void add(RetirementPayAndHealthInsurance retirementPayAndHealthInsurance);

	// update by empCd
	public void update(RetirementPayAndHealthInsurance retirementPayAndHealthInsurance);

	// details by empCd
	public RetirementPayAndHealthInsurance getRetirementPayAndHealthInsuranceByEmpCd(String empCd);


}
