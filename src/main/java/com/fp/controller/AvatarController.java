package com.fp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fp.config.WebAppInitializer;
import com.fp.model.User;

@Controller
public class AvatarController {

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public String getAvatar(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		User user = (User) request.getSession().getAttribute("user");
		String avatar = user.getProfilePicture();
		File file = new File(WebAppInitializer.LOCATION + avatar);
		try {
			Files.copy(file.toPath(), response.getOutputStream());
		} catch (IOException e) {
			request.setAttribute("error", "problem with the stream. Could not open output stream!");
		}
		return "main";
	}
}
