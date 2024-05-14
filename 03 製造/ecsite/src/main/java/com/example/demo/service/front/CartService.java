package com.example.demo.service.front;

import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminOrderHistoryFrom;

import java.time.LocalDateTime;
import java.util.List;

public interface CartService {
    public List<Stock> findCartListById(int user_id,int delete_flg);

    public void addCart(int stock_id, int user_id, LocalDateTime created_at, LocalDateTime updated_at, int delete_flg, int version);

    public int cartKazu(int user_id,int delete_flg);

    public void cartDelete(int delete_flg,int user_id,int id,LocalDateTime updateTime);

    public void cartPay(int delete_flg,int user_id);

    public List<Stock> cartQuantitySelect(int user_id ,int delete_flg);

    public void cartQuantityUpdate(int quantity ,int stock_id,LocalDateTime updateTime);

    public int addOrderHistoryAll(int stock_id, int user_id,int price, int quantity, LocalDateTime created_at,
                                                          LocalDateTime updated_at, int delete_flg, int version);
    
    public Stock cartSelectByStockId(int user_id,int orderId);
}
