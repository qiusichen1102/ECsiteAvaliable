package com.example.demo.service.front;

import java.util.List;

import com.example.demo.bean.front.Stock;

public interface StockService {
    public List<Stock> findAll();
    public Stock stockFindById(int id);
}
