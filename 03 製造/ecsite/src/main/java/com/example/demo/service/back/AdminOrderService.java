package com.example.demo.service.back;

import com.example.demo.bean.back.OrderHistory;
import com.example.demo.bean.front.Stock;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminOrderService {
    public List<Stock> orderHistory();

    public List<Stock> payFinish(int user_id,int delete_flg);

    public Stock stockFindById(int id);

    public List<Stock> orderFindByName(String name);

    public List<String> orderHistoryForUserName();

    public int orderDeleteById(int delete_flg,int id,LocalDateTime updated_at);

	public int selectOrderDelete(int id);
}
