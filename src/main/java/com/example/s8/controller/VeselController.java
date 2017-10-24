package com.example.s8.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.s8.model.UserDao;

@Controller
@RequestMapping(value = "/vesi")
public class VeselController {
	@Autowired
	ServletContext context;
	@Autowired
	UserDao dao;
	
	@RequestMapping(value = "/like", method = RequestMethod.GET)
	public String likeVesi(HttpSession s){
		dao.printSomeText();
		return "hivesi";
	}
	
	@RequestMapping(value = "/hi", method = RequestMethod.GET)
	public String hiVesi(Model model, HttpSession s){
		model.addAttribute("age", 10);
		s.setAttribute("godinki", 16);
		return "hivesi";
	}
	
	@RequestMapping(value = "/bye", method = RequestMethod.GET)
	public String byeVesi(){
		return "byevesi";
	}
	
	@RequestMapping(value = "dog/{dogNumber}/street/{streetNumber}/floor/{floorNumber}", method = RequestMethod.GET)
	public String getDog(Model m, 
			@PathVariable("dogNumber") Integer dogNum,
			@PathVariable("streetNumber") Integer streetNum,
			@PathVariable("floorNumber") Integer floorNum){
		m.addAttribute("doggy", dogNum);
		m.addAttribute("streety", streetNum);
		m.addAttribute("floory", floorNum);
		return "dog";
	}
		
}
