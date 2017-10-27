package com.fp.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fp.dbModel.UserDao;
import com.fp.utils.CommonUtils;

@Controller
public class FollowersAndFollowingImageController {

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/fetch-user-pic", method = RequestMethod.GET)
	public void fetchUserPicture(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("id") Long id) {
		if (id == null) {
			// TODO return response with error
		}

		String pic = null;
		try {
			pic = userDao.getUserPic(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		CommonUtils.showPicture(pic, response, request);
	}
}
