package com.example.demo.service.back;

import com.example.demo.bean.front.Stock;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminStockService {
    public List<Stock> stockFindAll();

    public Stock stockFindById(int id);

    public int addStock(int id, String name, String detail, int price, String imgpath, int quantity, LocalDateTime created_at, LocalDateTime updated_at,
                        int delete_flg, int version );

    public List<Stock> stockFindByName(String name);

    public int stockDeleteById(int id,LocalDateTime updated_at);

    public int stockUpdateById(String name,String detail,int price,String imgpath,int quantity,LocalDateTime updated_at,int id,int current_version);

	public int selectStockUpdateVersion(int id);
}
