package com.fp.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
	public String search(HttpServletRequest request, Model model, HttpServletResponse response, HttpSession session) {
		if (request.getSession().getAttribute("user") == null) {
			return "login";
		} else {
			String search = (String) request.getParameter("search");
			model.addAttribute("hideUploadPost", true);
			try {
				if (search != null) {
					if (search.charAt(0) == '@') {
						User searchUser = userDao.getUser(search.substring(1, search.length()));
						User loggedUser = (User) (request.getSession().getAttribute("user"));
						if (searchUser != null) {
							session.setAttribute("searchUser", searchUser);
							Set<User> followed = userDao
									.getAllFollowedForUser(loggedUser.getUserName());
							if(followed.contains(searchUser)){
								model.addAttribute("isFollowing",true);
							}
							else{
								model.addAttribute("isFollowing",false);
							}
							return "profile";
						}
						else{
							request.setAttribute("noUser"," don`t have user with this username :(");
							return "noFound";
						}
					} else {
						List<Post> posts = tagDao.searchPostByTag(search);
						if (posts != null) {
							model.addAttribute("posts", posts);
							request.getSession().setAttribute("posts", posts);
							model.addAttribute("currentPage", "search");
							return "posts";
						}
						else{
							request.setAttribute("noTag"," don`t have tag with this word :(");
							return "noFound";
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "error500";
			}
			return "search";
		}
	}
	
}