package com.example.demo.mapper.back;

import com.example.demo.bean.back.OrderHistory;
import com.example.demo.bean.front.Stock;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AdminOrderMapper {

	// orderHistoryリストの内容に基づいて対応するStockテーブルの結果をクエリする。created_atの結果はorderHistoryの結果となる
	@Select(" select o.id as orderId,s.name,s.price,s.imgpath,o.created_at,s.detail,s.quantity,u.name as userName,s.id  from order_history as o ,stock as s,user as u  where o.user_id=u.id and o.stock_id=s.id and o.delete_flg=0")
	public List<Stock> orderHistory();

	// orderHistoryリストの内容に基づいてユーザー名をクエリする
	@Select("select u.name from order_history as o ,stock as s,user as u  where o.user_id=u.id and o.stock_id=s.id")
	public List<String> orderHistoryForUserName();

	// delete_flgに基づいて対応するstock結果をクエリする
	@Select(" SELECT s.id,s.name,s.price,s.quantity,s.detail,s.imgpath from stock as s,(SELECT c.stock_id,c.user_id,c.id,c.delete_flg from cart AS c INNER  JOIN user as u where c.user_id=u.id and c.user_id=#{user_id}) AS r where s.id=r.stock_id and r.delete_flg=#{delete_flg}")
	public List<Stock> cartPayAll(@Param("user_id") int user_id, int delete_flg);

	// 支払い完了後、商品の結果をorder_historyテーブルに保存する
	@Insert("Insert into order_history(stock_id,user_id,price,quantity,created_at,updated_at,delete_flg,version) VALUES "
			+ "( #{stock_id},#{user_id},#{price},#{quantity},#{created_at},#{updated_at},#{delete_flg},#{version})")
	public int addOrderHistoryAll(int stock_id, int user_id, int price, int quantity, LocalDateTime created_at,
			LocalDateTime updated_at, int delete_flg, int version);

	// IDに基づいてstockテーブルの結果をクエリする
	@Select(" select o.id as orderId,s.name,s.price,s.imgpath,o.created_at,s.detail,s.quantity,u.name as userName,s.id  from order_history as o ,stock as s,user as u  where o.user_id=u.id and o.stock_id=s.id and o.id=#{order_id}")
	public Stock stockFindById(int order_id);

	// IDに基づいてstockテーブルの結果をクエリする
	
	//@Select(" select s.id,s.name,s.price,s.imgpath,o.created_at,s.detail,s.quantity from order_history as o ,stock as s,user as u  where o.user_id=u.id and o.stock_id=s.id  and s.name LIKE CONCAT('%', #{name}, '%')")
	@Select("SELECT o.id, u.name AS username, s.name, s.price, s.imgpath, o.created_at, s.detail, s.quantity FROM order_history AS o, stock AS s, user AS u WHERE o.user_id = u.id AND o.stock_id = s.id AND u.name LIKE CONCAT('%', #{name}, '%')")
	public List<Stock> orderFindByName(String name);

	// IDとdelete_flgに基づいてorder_historyを更新する
	@Update("update order_history set delete_flg = #{delete_flg},updated_at = #{updated_at} where id = #{id}")
	public int orderDeleteById(int delete_flg, int id,LocalDateTime updated_at);
	
	// IDに基づいてversionを検索する
	@Select("SELECT version FROM order_history WHERE id = #{id}")
	public int selectOrderDelete(int id);
}
