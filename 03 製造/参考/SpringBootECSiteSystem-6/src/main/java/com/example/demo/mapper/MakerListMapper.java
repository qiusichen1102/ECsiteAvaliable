package com.example.demo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.bean.MakerList;

@Mapper
public interface MakerListMapper {
	// list
	public List<MakerList> searchMakerListList();
	// list search
	public List<MakerList> search_manufacture(@Param("keyword")String keyword);
	
	// delete by manufacture_id
	public void MakerList_delete(String manufacture_id);
	// details by manufacture_id
	public MakerList getMakerListByManufacture_id(String manufacture_id);
	// update by manufacture_id
	public void makerList_update(MakerList makerList) ;
	// add
	public void Addmaker(MakerList makerList);

}

