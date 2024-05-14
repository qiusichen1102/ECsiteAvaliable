package com.example.demo.controller.back;

import com.example.demo.bean.front.Stock;
import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.form.front.StockForm;
import com.example.demo.service.back.AdminOrderServiceImpl;
import com.example.demo.service.back.AdminStockServiceImpl;
import com.example.demo.util.ImgDownloadUtil;
import com.example.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AdminOrderHistoryController {
    @Autowired
    private AdminOrderServiceImpl adminOrderServiceImpl;
    @Autowired
    private AdminStockServiceImpl adminStockServiceImpl;
    @Autowired
    private HttpSession session;
    private final int pageSize = 10;

    @GetMapping("/order")
    public String orderIn(Model model, @ModelAttribute("form") @Valid StockForm stockForm, @RequestParam(value = "name", required = false) String name, @RequestParam(defaultValue = "1") int pageNumber) {
        String errorMsg;
        AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
        System.out.println(name);
        if (adminUser != null) {
            if (name != null && !name.equals("")) {
                // キーワードで検索結果を取得する
                List<Stock> searcherRes = adminOrderServiceImpl.orderFindByName( name);
                List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(searcherRes, pageSize, pageNumber, session, model);
                model.addAttribute("orderHistory", PagedContactList);
                session.setAttribute("orderHistory", PagedContactList);
            } else {
                // 検索バーが空の場合、すべてのリストを表示する
                List<Stock> orderHistory = adminOrderServiceImpl.orderHistory();
                List<Stock> PagedContactList = (List<Stock>) PageUtil.PageCommon(orderHistory, pageSize, pageNumber, session, model);
                model.addAttribute("orderHistory", PagedContactList);
                session.setAttribute("orderHistory", PagedContactList);
            }
            return "admin/order";
        }
        errorMsg = "まだ登録していません、登録してください！";
        session.setAttribute("message", errorMsg);
        return "admin/login";
    }

    @GetMapping("/order_detail&{orderId}")
    public String orderDetail(@PathVariable String orderId, Model model) {
        String[] partsOrderId = orderId.split("="); // 等号 "=" で分割する
        // IDに基づいて詳細ページに遷移する
        Stock stock = adminOrderServiceImpl.stockFindById(Integer.parseInt(partsOrderId[1]));
        model.addAttribute("stock", stock);
        return "admin/order_detail";
    }

    @PostMapping("/order_delete&{orderId}")
    public String orderDelete(@PathVariable String orderId, Model model) {
    	int current_version = adminOrderServiceImpl.selectOrderDelete(Integer.parseInt(orderId));
        adminOrderServiceImpl.orderDeleteById(1, Integer.parseInt(orderId),LocalDateTime.now());
        List<Stock> orderHistory = adminOrderServiceImpl.orderHistory();
        model.addAttribute("orderHistory", orderHistory);
        return "admin/order";
    }
    
    //画像をストリームで表示する
    @GetMapping("/imgDownloadOrder")
    public void imgDownload(@RequestParam("stockid") String stockid, HttpServletResponse response) throws Exception {
        String decodedStockid = URLDecoder.decode(stockid, "UTF-8");
        Stock stock = adminStockServiceImpl.stockFindById(Integer.parseInt(decodedStockid));
        ImgDownloadUtil.downloadImage(stock.getImgpath(), response);
    }
}
