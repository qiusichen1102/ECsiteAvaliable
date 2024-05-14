package com.example.demo.service.front;

import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminOrderHistoryFrom;
import com.example.demo.mapper.back.AdminOrderMapper;
import com.example.demo.mapper.back.AdminStockMapper;
import com.example.demo.mapper.front.CartMapper;
import com.example.demo.service.front.CartService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AdminOrderMapper adminOrderMapper;
    @Override
    // 指定したユーザーのカート内のすべての商品情報を取得します。
    public List<Stock> findCartListById(int user_id,int delete_flg) {
        return cartMapper.cartAll(user_id, delete_flg);
    }

    @Override
    // カートに商品を追加します。
    public void addCart(@Param(value = "stock_id")int stock_id, @Param(value = "user_id")int user_id, LocalDateTime created_at, LocalDateTime updated_at, int delete_flg, int version) {
        cartMapper.cartAdd(stock_id, user_id, created_at, updated_at, delete_flg, version);
    }

    @Override
 // 指定したユーザーのカート内の商品数を取得します。
    public int cartKazu(int user_id,int delete_flg) {
        return cartMapper.cartKazu(user_id,delete_flg);
    }

    @Override
	// 指定したユーザーのカート内の商品を削除します。
    public void cartDelete(int delete_flg,int user_id,int id,LocalDateTime updateTime) {
        cartMapper.cartDelete(delete_flg,user_id,id, updateTime);
    }

    @Override
	// 指定したユーザーのカート内の商品を購入済みに設定します。
    public void cartPay(int delete_flg, int user_id) {
        cartMapper.cartPay(delete_flg,user_id);
    }

    @Override
	// 指定したユーザーのカート内の商品の情報を取得します。
    public List<Stock> cartQuantitySelect(int user_id,int delete_flg) {
        return cartMapper.cartQuantitySelect(user_id,delete_flg);
    }

    @Override
 // 指定した商品の在庫数量を更新します。
    public void cartQuantityUpdate(int quantity,int stock_id,LocalDateTime updateTime) {
        cartMapper.cartQuantityUpdate(quantity,stock_id,updateTime);
    }

    @Override
	// 支払い完了後、商品の結果をorder_historyテーブルに保存する
    public int addOrderHistoryAll(int stock_id, int user_id,int price, int quantity, LocalDateTime created_at, LocalDateTime updated_at, int delete_flg, int version) {
        return adminOrderMapper.addOrderHistoryAll(stock_id,user_id,price,quantity,created_at,updated_at,delete_flg,version);
    }

    @Override
 // 指定したユーザーと指定したorderIdのカート内の商品の情報を取得します。
    public Stock cartSelectByStockId(int user_id,int orderId){
        return cartMapper.cartSelectByStockId(user_id,orderId);
    }

}
