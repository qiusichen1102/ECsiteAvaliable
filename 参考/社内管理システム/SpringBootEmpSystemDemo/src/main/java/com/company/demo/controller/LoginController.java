package com.company.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company.demo.bean.EmpInfo;
import com.company.demo.bean.LoginUser;
import com.company.demo.common.Common;
import com.company.demo.form.EmpInfoForm;
import com.company.demo.service.LoginService;
@Controller
public class LoginController {
	@Autowired
	LoginService loginService;
	
	@GetMapping(value="/login") //登録画面
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@GetMapping(value="/login-error") //登録画面
	public ModelAndView loginError() {
		return new ModelAndView("login-error");
	}
	
	@GetMapping(value="/userlogin") //登録画面
	public ModelAndView userlogin() {
		return new ModelAndView("login");
	}
	
	@GetMapping(value="/loginList") //menu画面
	public ModelAndView menu(ModelAndView modelAndView) {
		List<LoginUser> loginUsers = loginService.getList();
		 modelAndView.addObject("loginUsers",loginUsers);
		return  modelAndView;
	}
	
	@GetMapping("/toLoginAdd")
	public ModelAndView toAdd(Model model) {

		return new ModelAndView("loginAdd");
	}
	@PostMapping("/loginAdd")
	public ModelAndView add(LoginUser loginUser, Model model, Map<String, Object> map) {

		
		LoginUser loginCheck = loginService.getInfoByAccount_id(loginUser.getAccount_id());
		if (loginCheck == null) {
			/*LoginUser login = new LoginUser(loginUser.getAccount_id(),loginUser.getRole_id(), loginUser.getPassword());*/
			loginService.addLogin(loginUser);
			return new ModelAndView("redirect:/loginList");
		} else {
			map.put("msg", "社員番号はすでに存在します！");

			return new ModelAndView("loginAdd");
		}
	}
	// delete by empCd
		@GetMapping("/loginDelete")
		public ModelAndView delete(@RequestParam(value = "id", required = false) String account_id, ModelAndView modelAndView) {
			loginService.loginDelete(account_id);
			System.out.println(account_id + ":" + "削除しました");
			modelAndView.setViewName("forward:/loginList");

			return modelAndView;
		}
		
		@GetMapping("/toLoginUpdate")
		public ModelAndView toLoginUpdate(@RequestParam("id") String account_id, Model model) {
			LoginUser login = loginService.getInfoByAccount_id(account_id);
			model.addAttribute("loginInfo", login);
			return new ModelAndView("loginUpdate");
		}
		@PostMapping("/loginUpdate")
		public ModelAndView update(LoginUser loginUser, Model model) {
			
			loginService.updateLogin(loginUser);
			return new ModelAndView("redirect:/loginList");
		}
}
