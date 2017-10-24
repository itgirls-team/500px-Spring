package com.example.s8.model;

import org.springframework.stereotype.Component;

@Component
public class UserDao {

	public void printSomeText(){
		System.out.println("Hi! I`m user dao ready do make SQL queries");
	}
}
