package com.fp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fp.model.User;
import com.fp.utils.CommonUtils;

@Controller
public class AvatarController {

	@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public void getAvatar(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String avatar;
		if (request.getParameter("profilePicture") != null) {
			avatar = (String) request.getParameter("profilePicture");
		} else {
			User user = (User) request.getSession().getAttribute("user");
			avatar = user.getProfilePicture();
		}
		CommonUtils.showPicture(avatar, response, request);
	}
}
