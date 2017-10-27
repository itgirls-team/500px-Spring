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
import com.fp.model.Album;
import com.fp.model.User;

@Controller
public class AlbumController {

	public static final String ALBUM_PICTURE_URL = "C:/pictures/";
	private static final String REG_SUCC_MSG = "Album crated successful";
	@Autowired
	UserDao userDao;
	@Autowired
	AlbumDao albumDao;

	// Album
	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public String showAlbums(HttpSession session, HttpServletRequest request) {
		try {
			User u = (User) request.getSession().getAttribute("user");
			User realUser = userDao.getUser(u.getUserName());
			realUser.setAlbumsOfUser(albumDao.getAllAlbumFromUser(realUser.getUserName()));
			request.getSession().setAttribute("user", realUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// request.getRequestDispatcher("album.jsp").forward(request, response);
		return "album";
	}

	// CreateAlbum
	@RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
	public String doPost(HttpServletRequest request, HttpServletResponse response) {
		String albumCategory = (String) request.getParameter("category");
		String validationMessage = validateInputData(albumCategory);
		if (validationMessage.equals(REG_SUCC_MSG)) {
			try {
				if (!albumDao.existAlbum(albumCategory)) {
					String albumImage = "defaultAlbumImage.jpg";
					Long userId = ((User) request.getSession().getAttribute("user")).getId();
					albumDao.createAlbum(new Album(albumCategory, albumImage, userId));
					User u = (User) request.getSession().getAttribute("user");
					User realUser = userDao.getUser(u.getUserName());
					realUser.setAlbumsOfUser(albumDao.getAllAlbumFromUser(realUser.getUserName()));
					request.getSession().setAttribute("user", realUser);
				} else {
					request.setAttribute("albumAlreadyExists", "This album category already exists!");
					return "createAlbum";
				}
			} catch (SQLException e) {
				request.setAttribute("error", "Problem with the database. Could not execute query!");
			}
		} else {
			request.setAttribute("emptyCategoryField", validationMessage);
			return "createAlbum";
		}

		return "album";
	}

	private String validateInputData(String category) {
		if (category == null) {
			return "Please fill all the required fields!";
		}
		return REG_SUCC_MSG;
	}
}
