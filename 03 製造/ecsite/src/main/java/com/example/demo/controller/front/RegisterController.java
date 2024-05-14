package com.example.demo.controller.front;

import com.example.demo.form.front.RegisterForm;

import com.example.demo.service.front.RegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class RegisterController {
    @Autowired
    private HttpSession session;
    @Autowired
    private RegisterServiceImpl registerServiceImpl;
 // クリックして新規登録画面に遷移する
    @GetMapping("/register")
    public String registerIn(){
        session.removeAttribute("message");
        return ("front/register");
    }

    // 作成成功のメッセージページ、失敗した場合は現在のページに戻る
    @PostMapping("/home2")
    public String register(@ModelAttribute("form") @Valid RegisterForm registerForm){
        String errorMsg;
        // すべてのユーザーを検索
        List<String> res = registerServiceImpl.AllAdmin();
        if(res!=null){
            for (String re : res) {
                // メールアドレスが重複していないかを確認し、重複している場合は現在のページに戻る
                if (re.equals(registerForm.getEmail())) {
                    errorMsg="メールアドレスが存在しています、他のメールアドレスでもう一度入力してください！";
                    session.setAttribute("message",errorMsg);
                    return ("front/register");
                }
            }
        }
        // 2回のパスワード入力が一致しているかを確認する
        if(!Objects.equals(registerForm.getPassword(), registerForm.getPassword_confirmation())){
            errorMsg="2回入力したパスワードは異なります、もう一度入力してください！";
            session.setAttribute("message",errorMsg);
            return ("front/register");
        }
        registerForm.setCreated_at(LocalDateTime.now());
        registerForm.setUpdated_at(LocalDateTime.now());
        // 登録成功した場合はデータベースに保存し、index2ページに戻る
        registerServiceImpl.register(registerForm);
        session.removeAttribute("message");
        return ("front/index2");
    }

}
