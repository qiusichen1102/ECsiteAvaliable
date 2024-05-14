package com.example.demo.mapper.front;

import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminOrderHistoryFrom;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CartMapper {
	// カートに商品を追加します。
	@Insert("Insert Into cart(stock_id,user_id,created_at,updated_at,delete_flg,version) VALUES (#{stock_id},#{user_id},#{created_at},#{updated_at},#{delete_flg},#{version})")
	public int cartAdd(int stock_id, int user_id, LocalDateTime created_at, LocalDateTime updated_at, int delete_flg,int version);

	// 指定したユーザーのカート内のすべての商品情報を取得します。
	@Select(" SELECT s.name,s.price,s.quantity,s.detail,s.imgpath,r.id from stock as s,(SELECT c.stock_id,c.user_id,c.id,c.delete_flg from cart AS c INNER  JOIN user as u where c.user_id=u.id and c.user_id=#{user_id}) AS r where s.id=r.stock_id and r.delete_flg=#{delete_flg}")
	public List<Stock> cartAll(@Param("user_id") int user_id, int delete_flg);

	// 指定したユーザーのカート内の商品数を取得します。
	@Select("Select count(*) from cart where user_id = #{user_id} and delete_flg=#{delete_flg}")
	public int cartKazu(int user_id, int delete_flg);

	// 指定したユーザーのカート内の商品を削除します。
	@Update("update cart set delete_flg=#{delete_flg} ,updated_at=#{updated_at} where user_id=#{user_id} and id=#{id}")
	public void cartDelete(int delete_flg, int user_id, int id,LocalDateTime updated_at);

	// 指定したユーザーのカート内の商品を購入済みに設定します。
	@Update("update cart set delete_flg=#{delete_flg} where user_id=#{user_id} AND delete_flg=0")
	public void cartPay(int delete_flg, int user_id);

	// 指定した商品の在庫数量を更新します。
	@Update("UPDATE  stock set quantity= #{quantity} ,updated_at=#{updated_at}   where  id = #{stock_id};")
	public void cartQuantityUpdate(int quantity, int stock_id,LocalDateTime updated_at);

	// 指定したユーザーのカート内の商品の情報を取得します。
	@Select("select s.id,s.name,s.detail,s.price,s.quantity from stock as s,cart as c , user as u where s.id = c.stock_id and u.id=#{user_id} and c.delete_flg = #{delete_flg}")
	public List<Stock> cartQuantitySelect(int user_id, int delete_flg);

	// 指定したユーザーと指定したorderIdのカート内の商品の情報を取得します。
	@Select("select s.id,s.name,s.detail,s.price,s.quantity,s.imgpath from stock as s,cart as c , user as u where s.id = c.stock_id and u.id=#{user_id}  and c.id=#{stock_id}")
	public Stock cartSelectByStockId(int user_id,int stock_id);
}
