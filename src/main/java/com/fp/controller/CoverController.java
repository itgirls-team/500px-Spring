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

import com.fp.dbModel.AlbumDao;
import com.fp.model.User;
import com.fp.utils.CommonUtils;

@Controller
public class CoverController {

	@Autowired
	private AlbumDao albumDao;

	@RequestMapping(value = "/fetch-cover", method = RequestMethod.GET)
	public void getAvatar(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam("id") Long id) {
		String cover = null;
		User user = (User) request.getSession().getAttribute("user");
		try {
			cover = albumDao.getCover(id);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		CommonUtils.showPicture(cover, response, request); 
	}
}
