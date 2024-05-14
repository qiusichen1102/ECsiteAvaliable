package com.example.demo.service.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bean.front.Stock;
import com.example.demo.mapper.front.StockMapper;
import com.example.demo.service.front.StockService;
@Service
public class StockServiceImpl  implements StockService {
	@Autowired
    private StockMapper stockMapper;

    @Override
	// 削除フラグが1でない全ての在庫を取得します。
    public List<Stock> findAll() {
        return stockMapper.selectAll();
    }
    
    @Override
    //IDに基づいてstockを検索する  
    public Stock stockFindById(int id){
        return stockMapper.stockFindById(id);

    }
}
