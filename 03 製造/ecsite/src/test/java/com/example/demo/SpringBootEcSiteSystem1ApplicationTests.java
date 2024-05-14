package com.example.demo;

import com.example.demo.bean.front.Stock;
import com.example.demo.service.back.AdminOrderService;
import com.example.demo.service.front.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class SpringBootEcSiteSystem1ApplicationTests {
	@Autowired
	CartService cartServiceImpl;
	@Autowired
	AdminOrderService adminOrderService;
	@Test
	void contextLoads() {
		List<Stock> orderHistory = adminOrderService.orderHistory();
		for (Stock stock : orderHistory) {
			System.out.println(stock);
		}

	}

}
