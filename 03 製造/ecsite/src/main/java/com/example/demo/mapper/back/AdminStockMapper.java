package com.example.demo.mapper.back;

import com.example.demo.bean.front.Stock;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AdminStockMapper {
	// delete_flgが0のすべての在庫情報を取得します。
	@Select("select * from stock where delete_flg = 0")
	public List<Stock> stockFindAdd();

	// 指定したIDに対応する在庫情報を取得します。
	@Select("select * from stock where id = #{id}")
	public Stock stockFindById(int id);

	// 商品情報を追加します。
	@Insert("Insert Into stock (id,name,detail,price,imgpath,quantity,created_at,updated_at,delete_flg,version) "
			+ "VALUES (#{id},#{name},#{detail},#{price},#{imgpath},#{quantity},#{created_at},#{updated_at},#{delete_flg},#{version})")
	public int addStock(int id, String name, String detail, int price, String imgpath, int quantity,
			LocalDateTime created_at, LocalDateTime updated_at, int delete_flg, int version);

	// 指定した名前に一致する在庫情報を検索します。
	@Select("SELECT * FROM stock WHERE name LIKE CONCAT('%', #{name}, '%')")
	public List<Stock> stockFindByName(String name);

    // 指定したIDに対応する在庫情報のdelete_flg更新します。
	@Update("Update  stock set delete_flg=1,updated_at = #{updated_at} where id = #{id}")
	public int stockDeleteById(int id,LocalDateTime updated_at);

	// 指定したIDに対応する在庫情報を更新します。
	@Update("Update stock set name = #{name},detail = #{detail},price = #{price},imgpath = #{imgpath},quantity = #{quantity},updated_at = #{updated_at},version = version + 1 where id = #{id} AND version = #{current_version}")
	public int stockUpdateById(String name, String detail, int price, String imgpath, int quantity,
			LocalDateTime updated_at, int id,int current_version);
	
	// 指定したIDに対応するVersionを検索します。
	@Select("SELECT version FROM stock WHERE  id = #{id}")
	public int selectStockUpdateVersion(int id);
}
