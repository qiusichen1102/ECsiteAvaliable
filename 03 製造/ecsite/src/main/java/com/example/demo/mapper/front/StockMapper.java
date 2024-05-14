package com.example.demo.mapper.front;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.bean.front.Stock;

@Mapper
public interface StockMapper {
	// 削除フラグが1でない全ての在庫を取得します。
	@Select("select * from stock where delete_flg <> 1")
	public List<Stock> selectAll();

	// 指定された範囲の在庫を取得します。
	// currIndex: 現在のインデックス
	// pageSize: ページサイズ
	@Select("select * from stock limit #{currIndex},#{pageSize}")
	public List<Stock> selectCount(Map<String, Object> data);
	
	//IDに基づいてstockを検索する    
	@Select("select * from stock where id = #{id} ")
	public Stock stockFindById(int id);
}
