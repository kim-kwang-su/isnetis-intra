package com.isnet.mgr.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value="/login.isnet")
	public ModelAndView login(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/login");
		return mav;
	}
	
	@RequestMapping(value="/home.isnet", method=RequestMethod.GET)
	public ModelAndView home(){
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/home");
		return mav;
	}
}
