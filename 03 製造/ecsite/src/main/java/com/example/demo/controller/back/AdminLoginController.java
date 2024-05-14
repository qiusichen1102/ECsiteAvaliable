package com.example.demo.controller.back;

import com.example.demo.bean.front.admin;
import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.service.back.AdminLoginService;
import com.example.demo.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private HttpSession session;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/AdminLogin")
    public String adminIn(){
    	//ログインページにアクセスするたびに、errorMSGのセッションを削除します
        session.removeAttribute("message");
        //すべてのクッキーを取得する
        Cookie[] cookies = request.getCookies();
        //rememberMeTokenの受け取りオブジェクト
        String rememberMeToken = null;
        //目標のクッキーが存在するかどうかを調べる
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rememberMeToken")) {
                	//目標のクッキーを検出したら、ループを終了し、その値をrememberMeTokenに割り当てます
                    rememberMeToken = cookie.getValue();
                    break;
                }
            }
        }
        //もしクッキーが存在する場合、クッキーの値を使用してadminUserが存在するかどうかを検索します
        if (rememberMeToken != null) {
            AdminLoginFrom adminUser = adminLoginService.findUserByRemember_token(rememberMeToken);
            if (adminUser != null) {
            	//adminUserが存在する場合、ログイン成功です。セッションを保存します
                session.setAttribute("adminUser",adminUser);
                //セッションの有効期限を10分に設定します
                session.setMaxInactiveInterval(600);
                return "admin/home";
            }
        }else{
        	 //もしクッキーが存在しない場合、セッションをクリアしてログインページに移動します
            session.removeAttribute("adminUser");
        }
        return "admin/login";
    }


    @PostMapping("/home")
    public String Login(@ModelAttribute("form") @Valid AdminLoginFrom adminLoginFrom) {
        //入力されたアカウントとパスワードを検証する
        AdminLoginFrom adminUser = adminLoginService.AdminLogin(adminLoginFrom.getEmail(), adminLoginFrom.getPassword());
        String errorMsg;
        if(adminUser != null ){
        	int current_version = adminLoginService.selectAdminLast(adminLoginFrom.getEmail(), adminLoginFrom.getPassword());
        	adminLoginService.setAdminLast_login_at(adminLoginFrom.getEmail(), adminLoginFrom.getPassword(),LocalDateTime.now(),current_version);
            //セッションに保存する
            session.setAttribute("adminUser",adminUser);
            //セッションの有効期限を10分に設定します
            session.setMaxInactiveInterval(600);
            System.out.println(adminLoginFrom.isRememberMe());
            // 「Remember Me」が選択されたかどうかをチェックする
            if (adminLoginFrom.isRememberMe()) {
                String rememberMeToken = generateRememberMeToken();
                // 「Remember Me」が選択されたかどうかをチェックする
                storeRememberMeTokenInCookie(rememberMeToken, response);
                int current_version2 = adminLoginService.selectRemember_token(adminUser.getId());
                adminLoginService.setRemember_token(rememberMeToken,adminUser.getId(),LocalDateTime.now(),current_version2);
            }
            return "admin/home";
        }
        //失敗した場合はエラーメッセージを表示してログインページに戻る
        errorMsg="メールアドレス或いはパスワード間違いました、もう一回入力してください！";
        session.setAttribute("message",errorMsg);
        return "admin/login";
    }

    @GetMapping("/home")
    public String Home(){
        AdminLoginFrom adminUser = (AdminLoginFrom) session.getAttribute("adminUser");
        if(adminUser != null ){
            return "admin/home";
        }
        //失敗した場合はログインページに戻る
        return "admin/login";
    }
    
    @GetMapping("/AdminLogout")
    public String LogOut(){
        // ログアウト時にセッションをクリアする
    	 if(session.getAttribute("adminUser")!=null){
            session.removeAttribute("adminUser");
             removeRememberMeTokenFromCookie(response);
    	 }
        return "admin/login";
    }

    private String generateRememberMeToken() {
        // "Remember Me"トークンを生成する
        return UUID.randomUUID().toString();
    }
    
 // "Remember Me"トークンをCookieに保存するロジック
    private void storeRememberMeTokenInCookie(String rememberMeToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("rememberMeToken", rememberMeToken);
        cookie.setMaxAge(10*60); // Cookieの有効期限を設定する
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //Cookieクリア
    private void removeRememberMeTokenFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("rememberMeToken", null);
        cookie.setMaxAge(0); // 有効期限を0に設定し、即座に期限切れにする
        cookie.setPath("/"); // 以前に作成したCookieと同じパスを設定する
        response.addCookie(cookie);
    }


}
