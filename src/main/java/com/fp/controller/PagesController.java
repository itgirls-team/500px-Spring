package com.fp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/page")
public class PagesController {

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String getRegisterPage() {
		return "register";
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout";
	}


	@GetMapping("/follow")
	public String getFollowPage() {
		return "follow";
	}

	@GetMapping("/unfollow")
	public String getUnfollowPage() {
		return "unfollow";
	}

	@GetMapping("/following")
	public String getFollowingPage() {
		return "following";
	}

	@GetMapping("/followers")
	public String getFollowersPage() {
		return "followers";
	}

	@GetMapping("/addNewAlbum")
	public String getCreateAlbumPage() {
		return "createAlbum";
	}

}
