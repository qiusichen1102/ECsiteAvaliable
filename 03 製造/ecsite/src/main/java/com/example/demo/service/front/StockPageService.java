package com.example.demo.service.front;

import com.example.demo.bean.front.Stock;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface StockPageService {
    public List<Stock> pageForStock(int currIndex,int pageSize);
    public PageInfo<Stock> queryStockList(Integer pageNum, Integer pageSize);
}
