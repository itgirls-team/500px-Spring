package com.fp.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fp.dbModel.AlbumDao;
import com.fp.dbModel.UserDao;
import com.fp.model.User;

@Controller
public class AlbumController {
 
	@Autowired
	UserDao userDao;
	@Autowired
	AlbumDao albumDao;
	public static final String ALBUM_PICTURE_URL = "C:/pictures/";
	
	
	//Album
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public String showAlbums(HttpSession session, HttpServletRequest request){
	try {
		 User u = (User) request.getSession().getAttribute("user");
		 User realUser = userDao.getUser(u.getUserName());
		 realUser.setAlbumsOfUser(albumDao.getAllAlbumFromUser(realUser.getUserName()));
		 request.getSession().setAttribute("user", realUser);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		 //request.getRequestDispatcher("album.jsp").forward(request, response);
		return "album";
	}
	
	
	//CreateAlbum 
	@RequestMapping(value = "/CreateAlbum" , method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response){
		return "createAlbum.jsp";
	}

}
