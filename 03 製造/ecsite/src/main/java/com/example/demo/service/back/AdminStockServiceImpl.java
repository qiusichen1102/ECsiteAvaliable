package com.example.demo.service.back;

import com.example.demo.bean.front.Stock;
import com.example.demo.mapper.back.AdminStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminStockServiceImpl implements AdminStockService {
    @Autowired
    private AdminStockMapper adminStockMapper;
    
    // delete_flgが0のすべての在庫情報を取得します。
    @Override
    public List<Stock> stockFindAll() {
        return adminStockMapper.stockFindAdd();
    }

	// 指定したIDに対応する在庫情報を取得します。
    @Override
    public Stock stockFindById(int id) {
        return adminStockMapper.stockFindById(id);
    }

	// 商品情報を追加します。
    @Override
    public int addStock(int id, String name, String detail, int price, String imgpath, int quantity, LocalDateTime created_at, LocalDateTime updated_at, int delete_flg, int version) {
        return adminStockMapper.addStock(id,name,detail,price,imgpath,quantity,created_at,updated_at,delete_flg,version);
    }

	// 指定した名前に一致する在庫情報を検索します。
    @Override
    public List<Stock> stockFindByName(String name) {
        return adminStockMapper.stockFindByName(name);
    }

	// 指定したIDに対応する在庫情報のdelete_flg更新します。
    @Override
    public int stockDeleteById(int id,LocalDateTime updated_at) {
        return adminStockMapper.stockDeleteById(id,updated_at);
    }

	// 指定したIDに対応する在庫情報を更新します。
    @Override
    public int stockUpdateById(String name, String detail, int price, String imgpath, int quantity, LocalDateTime updated_at, int id,int current_version) {
        return adminStockMapper.stockUpdateById(name,detail,price,imgpath,quantity,updated_at,id,current_version);
    }
    
	// 指定したIDに対応するVersionを検索します。
	@Override	
	public int selectStockUpdateVersion(int id) {
		return adminStockMapper.selectStockUpdateVersion(id);
	}

}
