package com.example.demo.service.back;

import com.example.demo.bean.back.OrderHistory;
import com.example.demo.bean.front.Stock;
import com.example.demo.mapper.back.AdminOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService{
    @Autowired
    private AdminOrderMapper adminOrderMapper;
	// orderHistoryリストの内容に基づいて対応するStockテーブルの結果をクエリする。created_atの結果はorderHistoryの結果となる   
    @Override
    public List<Stock> orderHistory() {
        return adminOrderMapper.orderHistory();
    }

	// delete_flgに基づいて対応するstock結果をクエリする
    @Override
    public List<Stock> payFinish(int user_id,int delete_flg) {
        return adminOrderMapper.cartPayAll( user_id, delete_flg);
    }

	// IDに基づいてstockテーブルの結果をクエリする
    @Override
    public Stock stockFindById(int id) {
        return adminOrderMapper.stockFindById(id);
    }

	// IDに基づいてstockテーブルの結果をクエリする
    @Override
    public List<Stock> orderFindByName(String name){
        return adminOrderMapper.orderFindByName(name);
    }

	// orderHistoryリストの内容に基づいてユーザー名をクエリする
    @Override
    public List<String> orderHistoryForUserName() {
        return adminOrderMapper.orderHistoryForUserName();
    }

	// IDとdelete_flgに基づいてorder_historyを更新する
    @Override
    public int orderDeleteById(int delete_flg, int id,LocalDateTime updated_at) {
        return adminOrderMapper.orderDeleteById(delete_flg,id,updated_at);
    }

	// IDに基づいてversionを検索する
	@Override
	public int selectOrderDelete(int id) {
		return adminOrderMapper.selectOrderDelete(id);
	}


}
