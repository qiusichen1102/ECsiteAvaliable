package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MeunController {

//	//menu画面
//	@RequestMapping(value = "/meun", method = RequestMethod.POST)
//	public String meun(Model model) {
//		return "/meun";
//	}
	
	@PostMapping(value = "/meun") //menuに戻る画面
	public ModelAndView meun2() {
		return new ModelAndView("meun");
	}
	
	@GetMapping(value = "/meun") //表示menu画面
	public ModelAndView meun() {
		return new ModelAndView("meun");
	}
}
