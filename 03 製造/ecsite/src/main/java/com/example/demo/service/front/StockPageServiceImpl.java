package com.example.demo.service.front;

import com.example.demo.bean.front.Stock;
import com.example.demo.mapper.front.StockMapper;
import com.example.demo.service.front.StockPageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockPageServiceImpl implements StockPageService {
    @Autowired
    private StockMapper stockMapper;
    @Override
    //ページングデータの設定
    public List<Stock> pageForStock(int currPage, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        data.put("currIndex",(currPage-1)*pageSize);
        data.put("pageSize",pageSize);
        return stockMapper.selectCount(data);
    }
    @Override
    //商品の総数に基づいてページ分割する
    public PageInfo<Stock> queryStockList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Stock> stocks = stockMapper.selectAll();
        PageInfo<Stock> stockPageInfo = new PageInfo<>(stocks,pageSize);
        return stockPageInfo;

    }
}
