package com.company.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class MenuController {


	@GetMapping(value="/") //menu画面
	public ModelAndView menu1() {
		return new ModelAndView("menu");
	}
	
	@GetMapping(value="/menu") //menu画面
	public ModelAndView menu2() {
		return new ModelAndView("menu");
	}

}
