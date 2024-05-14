package com.example.demo.controller.front;

import com.example.demo.bean.front.User;
import com.example.demo.bean.front.admin;
import com.example.demo.form.back.AdminLoginFrom;
import com.example.demo.form.front.LoginForm;
import com.example.demo.service.front.LoginServiceImpl;
import com.example.demo.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private HttpSession session;
    @Autowired
    private LoginServiceImpl loginServiceImpl;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;
    // ログイン認証成功後、ホームにリダイレクト。失敗時は同じページに戻る。
    @PostMapping("/index2")
    public String login(@ModelAttribute("form") @Valid LoginForm loginForm) throws IOException {
        User loginUser = loginServiceImpl.Login(loginForm.getEmail(),loginForm.getPassword());
        String errorMsg;
        if(loginUser!=null){
            // バリデーションコードの確認
        	 String sessionCode = (String) session.getAttribute("JCCODE");
             String userCode = loginForm.getVerifycode();
            if(sessionCode != null && sessionCode.equalsIgnoreCase(userCode)){
                // ログイン成功時、ログイン情報をセッションに保存
                loginServiceImpl.setLast_login_at(loginForm.getEmail(),loginForm.getPassword(), LocalDateTime.now());
                session.setAttribute("user",loginServiceImpl.Login(loginForm.getEmail(),loginForm.getPassword()));
                session.setMaxInactiveInterval(600);
                session.removeAttribute("message");
                System.out.println(loginForm.isRemember());
                if (loginForm.isRemember()) {
                    String rememberMeToken = generateRememberMeToken();
                    // 「Remember Me」トークンをユーザーのブラウザのCookieに保存
                    storeRememberMeTokenInCookie(rememberMeToken, response);
                    loginServiceImpl.setRemember_token(rememberMeToken,loginUser.getId());
                }
                if("5aXRj36XqsUcb4HdfUPfO3AhMz4Hkt2MCLr7CH6g".equals(session.getAttribute("_token"))) {
                	return "front/contact";
                }
                return "front/index2";
            }else{
                errorMsg="検証コードが間違っています。もう一度入力してください。";
                session.setAttribute("message",errorMsg);
                return "front/login";
            }
        }
        // ログイン失敗時はログインページに戻る
        errorMsg="メールアドレスまたはパスワードが間違っています。もう一度入力してください。";
        session.setAttribute("message",errorMsg);
        return "front/login";
    }


    @GetMapping("/login")
    public String loginIn() {
        session.removeAttribute("message");
        session.removeAttribute("_token");
        String rememberMeToken = CookieUtil.checkCookie(request, "rememberMeTokenF");
        if (rememberMeToken != null) {
            User loginUser = loginServiceImpl.findUserByRemember_token(rememberMeToken);
            if (loginUser != null) {
                session.setAttribute("user", loginUser);
                session.setMaxInactiveInterval(600);
                return "front/index";
            }
        } else {
            session.removeAttribute("user");
        }
        return "front/login";
    }

    @GetMapping("/logOut")
    public String logOut(@ModelAttribute("form") @Valid LoginForm loginForm){
        // ログアウト後、セッションをクリアしてindexページに戻る
        session.removeAttribute("user");
        removeRememberMeTokenFromCookie(response);
        return "front/index";
    }

    private String generateRememberMeToken() {
        // 「Remember Me」トークンを生成する
        return UUID.randomUUID().toString();
    }

    private void storeRememberMeTokenInCookie(String rememberMeToken, HttpServletResponse response) {
        // 「Remember Me」トークンをCookieに保存するロジック
        Cookie cookie = new Cookie("rememberMeTokenF", rememberMeToken);
        cookie.setMaxAge(10*60); // Cookieの有効期限を設定
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void removeRememberMeTokenFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("rememberMeTokenF", null);
        cookie.setMaxAge(0); // 有効期限を0に設定して即座に無効化
        cookie.setPath("/"); // 以前に作成したCookieと同じパスを設定
        response.addCookie(cookie);
    }

}
