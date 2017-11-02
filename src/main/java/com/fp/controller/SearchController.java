package com.fp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fp.dbModel.TagDao;
import com.fp.dbModel.UserDao;
import com.fp.model.Post;
import com.fp.model.User;

@Controller
public class SearchController {

	@Autowired
	TagDao tagDao;
	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(HttpServletRequest request, Model model, HttpServletResponse response,HttpSession session) {

		String search = (String) request.getParameter("search");
		try {
			if (search != null) {
				if (search.charAt(0) == '@') {
					User user = userDao.getUser(search.substring(1, search.length()));
					if(user != null){
						model.addAttribute("searchUser", user);
						session.setAttribute("searchUser", user);
						return "profile";
					}
				} else {
					List<Post> posts = tagDao.searchPostByTag(search);
					if (posts != null) {
						//model.addAttribute("hideUploadPost",true);
						model.addAttribute("posts", posts);
						request.getSession().setAttribute("posts", posts);
						model.addAttribute("currentPage", "search");
						return "posts";
					}
				}
			}
		} catch (SQLException e) {
			model.addAttribute("exception", "SQLException");
			model.addAttribute("getMessage", e.getMessage());
		}
		return "search";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfile(HttpServletRequest request, Model model, HttpServletResponse response) {
		try {
			model.addAttribute("searchUser",userDao.getUser((String)request.getParameter("searchUsername")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "profile";
	}
	
	
}
