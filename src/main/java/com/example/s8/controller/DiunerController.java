package com.example.s8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.s8.model.Diuner;
import com.example.s8.model.DiunerDao;

@Controller
public class DiunerController {

	@Autowired
	DiunerDao dao;
	
	@RequestMapping(value="/addDiuner", method = RequestMethod.GET)
	public String addDiuner(Model m){
		Diuner d = new Diuner();
		m.addAttribute("diunerche", d);
		return "addDiuner";
	}
	
	@RequestMapping(value="/addDiuner", method = RequestMethod.POST)
	public String saveDiuner(@ModelAttribute Diuner readyDiuner){
		dao.insertDuner(readyDiuner);
		return "redirect:html/success.html";
	}
	
}
