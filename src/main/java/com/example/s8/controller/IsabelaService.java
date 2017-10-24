package com.example.s8.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.s8.model.Diuner;

@RestController
public class IsabelaService {

	@RequestMapping(value = "/name", method=RequestMethod.GET)
	@ResponseBody
	public String getName(){
		return "Bravo isabela!";
	}
	
	@RequestMapping(value = "/diuner", method=RequestMethod.GET)
	@ResponseBody
	public Diuner getDiuner(){
		Diuner d = new Diuner();
		d.setGramaj(2300);
		d.setMeso("Svinchyga");
		d.setHlebche("Debelo");
		d.setKartofi(6);
		return d;
	}
}
