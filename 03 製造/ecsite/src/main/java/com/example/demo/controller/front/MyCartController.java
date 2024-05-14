package com.example.demo.controller.front;


import com.example.demo.bean.front.Stock;
import com.example.demo.bean.front.User;
import com.example.demo.service.back.AdminOrderServiceImpl;
import com.example.demo.service.front.CartServiceImpl;
import com.example.demo.util.ImgDownloadUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MyCartController {
    @Autowired
    private HttpSession session;
    @Autowired
    private CartServiceImpl cartServiceImpl;
    @Autowired
    private AdminOrderServiceImpl adminOrderServiceImpl;

    @PostMapping("/myCart")
    public String myCart2In(){
        // ログインしていない場合はログインページに戻る。ログインしている場合はカートページに進む
        if(session.getAttribute("user")!=null){
            return ("front/myCart");
        }
        String errorMsg;
        errorMsg="まだ登録していません、登録してください！";
        session.setAttribute("message",errorMsg);
        return ("front/login");
    }

    @RequestMapping(value="/myCart&{id}",method = RequestMethod.POST)
    public String myCartIn( @PathVariable String id ){
        if(session.getAttribute("user")!=null){
            User user = (User) session.getAttribute("user");
            // クリックした商品をデータベースに追加する
            cartServiceImpl.addCart(Integer.parseInt(id),user.getId(),LocalDateTime.now(),LocalDateTime.now(),0,1);
            // ページをリフレッシュしてカートページに表示する
            refresh();
            return ("front/myCart");
        }
        // ログインしていない場合はログインページに戻る
        return ("front/login");
    }

    @GetMapping("/myCart")
    // カートのアイコンをクリックして直接カートページに進む。ログインしていない場合はログインページに戻る
    public String myCart3In(){
        if(session.getAttribute("user")!=null){
            refresh();
            return ("front/myCart");
        }
        return ("front/login");
    }


    // 「購入する」ボタンをクリックしてmyCart2ページに進む
    @PostMapping("/myCart2")
    public String myCart4In(){
        return ("front/myCart2");
    }

    @PostMapping("/pay")
    public String pay(){
        User user = (User) session.getAttribute("user");
        // カート内でflgが0の商品リストを取得する
        List<Stock> stocksForId = cartServiceImpl.cartQuantitySelect(user.getId(),0);

        for (Stock stock : stocksForId) {
            if(stock.getQuantity()>0){
                // 商品IDを使用して在庫を更新し、数量を1減らす
                cartServiceImpl.cartQuantityUpdate(stock.getQuantity()-1,stock.getId(),LocalDateTime.now());
                cartServiceImpl.addOrderHistoryAll(stock.getId(),user.getId(),stock.getPrice(),1,LocalDateTime.now(),LocalDateTime.now(),0,1);
            }
        }
        // 支払いが成功した商品のflgを2に変更する
        cartServiceImpl.cartPay(2,user.getId());
        // 購入履歴リスト（将来的に購入履歴を表示するために使用する）
        List<Stock> stocks = adminOrderServiceImpl.payFinish(user.getId(), 2);

        // ページをリフレッシュする
        refresh();
        return ("front/myCart");
    }

    @RequestMapping(value="/delete&{id}",method = RequestMethod.POST)
    public String delete( @PathVariable String id ){
        User user = (User) session.getAttribute("user");
        // カートから商品を削除し、flgを1に変更して非表示にする
        cartServiceImpl.cartDelete(1, user.getId(), Integer.parseInt(id),LocalDateTime.now());
        // ページをリフレッシュする
        refresh();
        return("front/myCart");
    }

    // ページをリフレッシュする共通メソッド
    private void refresh(){
        User user = (User) session.getAttribute("user");
        // 現在のユーザーのカート内のすべての商品リストを取得し、セッションに保存してフロントエンドに渡す
        List<Stock> carts = cartServiceImpl.findCartListById(user.getId(),0);
        if(carts.isEmpty()){
            session.removeAttribute("carts");
        }else{
            session.setAttribute("carts",carts);
        }
        // 現在のユーザーのカート内のすべての商品の数量を取得し、セッションに保存してフロントエンドに渡す
        int cartKazu = cartServiceImpl.cartKazu(user.getId(),0);
        session.setAttribute("cartKazu",cartKazu);
        // 現在のユーザーのカート内のすべての商品の価格の合計を取得し、セッションに保存してフロントエンドに渡す
        int price=0;
        for (Stock cart : carts) {
            price += cart.getPrice();
        }
        session.setAttribute("price",price);
    }
    
    //画像をストリームで表示する
    @GetMapping("/imgDownloadCart")
	public void imgDownloadPhoto(@RequestParam("cartsId") String cartsId, HttpServletResponse response) throws Exception {
    	 User user = (User) session.getAttribute("user");
		String decodedStockid = URLDecoder.decode(cartsId, "UTF-8");
		Stock stock = cartServiceImpl.cartSelectByStockId(user.getId(),Integer.parseInt(decodedStockid));
		ImgDownloadUtil.downloadImage(stock.getImgpath(), response);
	}
}
