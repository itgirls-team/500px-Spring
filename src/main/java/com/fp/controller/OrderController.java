package com.fp.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fp.dbModel.PostDao;
import com.fp.model.Post;

@Controller
public class OrderController {
	
	@Autowired 
	PostDao postDao;

	@RequestMapping(value="/posts/{param}", method = RequestMethod.GET)
	public String sortAlbumPosts(HttpSession session, Model model, @PathVariable("param") String param) {
		model.addAttribute("currentPage", "posts");
		sortPosts(param,session,model);
		return "posts";
	}
	@RequestMapping(value="/search/{param}", method = RequestMethod.GET)
	public String sortSearchPosts(HttpSession session, Model model, @PathVariable("param") String param){
		model.addAttribute("currentPage", "search");
		sortPosts(param,session,model);
		return "search";
	}
	@RequestMapping(value="/newsfeed/{param}", method = RequestMethod.GET)
	public String sortPostsBySearchUser(HttpSession session, Model model, @PathVariable("param") String param){
		model.addAttribute("currentPage", "newsfeed");
		sortPosts(param,session,model);
		return "posts";
	}
	private void sortPosts(String param,HttpSession session,Model model){
		List<Post> posts = new ArrayList<> ((HashSet<Post>)session.getAttribute("posts"));
		switch (param) {
		case "date":
			sortByDate(posts);
			session.setAttribute("sort", "date");
			break;
		case "like":
			sortByLikes(posts);
			session.setAttribute("sort", "like");			
			break;
		default:
			sortByDate(posts);
			session.setAttribute("sort", "date");
			break;
		}
		model.addAttribute("posts", posts);
	}
	private void sortByLikes(List<Post> posts){
		Collections.sort(posts, new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o2.getCountsOfLikes() - o1.getCountsOfLikes();
			}
		});
	}
	
	private void sortByDate(List<Post> posts){
		Collections.sort(posts, new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o2.getDateOfUpload().compareTo(o1.getDateOfUpload());
			}
		});
	}
	

}
