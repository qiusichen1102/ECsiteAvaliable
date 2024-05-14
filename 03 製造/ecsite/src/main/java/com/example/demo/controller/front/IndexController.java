package com.example.demo.controller.front;

import com.example.demo.bean.front.User;
import com.example.demo.service.front.LoginServiceImpl;
import com.example.demo.util.CookieUtil;
import com.example.demo.util.ImgDownloadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bean.front.Stock;
import com.example.demo.service.front.StockServiceImpl;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    private HttpSession session;
    @Autowired
    private StockServiceImpl stockServiceimpl;
    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    HttpServletRequest request;
    private int pageSize = 9;

    // プロジェクトのエントリーポイント、デフォルトでは最初のページを表示します
    @GetMapping("/index")
    public String indexIn(@RequestParam(defaultValue = "1") int pageNumber) {
        String rememberMeToken = CookieUtil.checkCookie(request, "rememberMeTokenF");
        List<Stock> stockList = stockServiceimpl.findAll();
        int totalItems = stockList.size();
        int totalPages = (totalItems + pageSize - 1) / pageSize;

        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<Stock> pagedStockList = stockList.subList(fromIndex, toIndex);

        session.setAttribute("StockList", pagedStockList);
        session.setAttribute("totalPages", totalPages);
        session.setAttribute("currentPage", pageNumber);
        if (rememberMeToken != null) {
            User loginUser = loginServiceImpl.findUserByRemember_token(rememberMeToken);
            if (loginUser != null) {
                session.setAttribute("user", loginUser);
                session.setMaxInactiveInterval(600);
                return "front/index2";
            }
        }
        if (session.getAttribute("user") != null) {
            return "front/index2";
        } else {
            return "front/index";
        }

    }

    // ホームに戻る
    @GetMapping("/index2")
    public String index2(@RequestParam(defaultValue = "1") int pageNumber) {
        if (session.getAttribute("user") != null) {
            List<Stock> stockList = stockServiceimpl.findAll();
            int totalItems = stockList.size();
            int totalPages = (totalItems + pageSize - 1) / pageSize;

            int fromIndex = (pageNumber - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalItems);
            List<Stock> pagedStockList = stockList.subList(fromIndex, toIndex);

            System.out.println(totalPages + pageNumber);
            session.setAttribute("StockList", pagedStockList);
            session.setAttribute("totalPages", totalPages);
            session.setAttribute("currentPage", pageNumber);
            return "front/index2";
        } else {
            return "front/index";
        }
    }
    
    //画像をストリームで表示する
    @GetMapping("/imgDownloadIndex")
    public void imgDownloadIndex(@RequestParam("stock") String stock, HttpServletResponse response) throws Exception {
        String decodedStockid = URLDecoder.decode(stock, "UTF-8");
        Stock stockById = stockServiceimpl.stockFindById(Integer.parseInt(decodedStockid));
        ImgDownloadUtil.downloadImage(stockById.getImgpath(), response);
    }
}
