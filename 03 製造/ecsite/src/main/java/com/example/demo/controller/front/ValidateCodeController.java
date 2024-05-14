package com.example.demo.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.util.ValidateCodeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ValidateCodeController {
	// 验证コード画像を返す
	@GetMapping("/getCaptchaImg")
	public void getCaptchaImg(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	    try {
	        response.setContentType("image/png");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setHeader("Expire", "0");
	        response.setHeader("Pragma", "no-cache");
	        ValidateCodeUtil validateCode = new ValidateCodeUtil();
	        // getRandomCodeImageメソッドは生成された検証コードの画像を直接responseに書き込みます
	        validateCode.getRandomCodeImage(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
