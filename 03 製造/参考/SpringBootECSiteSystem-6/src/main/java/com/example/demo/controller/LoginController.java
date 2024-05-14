package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

@Controller
public class LoginController {
	
	@Resource
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute("loginform") LoginForm loginform, Model model) {
		return ("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("loginform") @Valid LoginForm loginform,
			BindingResult result, ModelAndView model, HttpSession session) {

		if (result.hasErrors()) {
			List<ObjectError> errorList = result.getAllErrors();
			// エラーメッセージを画面返却情報に入れる
			model.addObject("errorList", errorList);
			// 画面遷移の指定
			model.setViewName("login");
			return model;
		}

		List<String> errorlist = loginService.getResult(loginform);
		if (!(errorlist.size() == 0)) {
			model.addObject("message", errorlist.get(0));
			// 画面遷移の指定
			model.setViewName("login");
			return model;
		} else {
			// 画面遷移の指定
			session.setAttribute("userName", loginform.getAdmin_email_address());
			session.setAttribute("password", loginform.getAdmin_password());
			model.setViewName("meun");
			return model;
		}

	}
}