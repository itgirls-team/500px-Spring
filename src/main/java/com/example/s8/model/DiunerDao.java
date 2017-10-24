package com.example.s8.model;

import org.springframework.stereotype.Component;

@Component
public class DiunerDao {

	public void insertDuner(Diuner d){
		System.out.println("Diuner insert!");
		System.out.println(d);
	}
}
