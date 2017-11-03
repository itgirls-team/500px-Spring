package com.fp.controller;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String showAlbums(HttpSession session, HttpServletRequest request,Model model) {
		try {
			User realUser;
			if (request.getParameter("searchUser") != null) {
				realUser = userDao.getUser((String) request.getParameter("searchUser"));
				model.addAttribute("hideCreateAlbum",true);
			} else {
				User u = (User) request.getSession().getAttribute("user");
				realUser = userDao.getUser(u.getUserName());
				model.addAttribute("hideCreateAlbum",false);
			}
			Set<Album> albums = albumDao.getAllAlbumFromUser(realUser.getUserName());
			realUser.setAlbumsOfUser(albums);
			request.getSession().setAttribute("albums", realUser.getAlbumsOfUser());
		} catch (SQLException e) {
			e.printStackTrace();
			return "error500";
		}
		return "album";
	}

	// CreateAlbum
	@RequestMapping(value = "/createAlbum", method = RequestMethod.POST)
	public String doPost(HttpServletRequest request, HttpServletResponse response,Model model) {
		String albumCategory = (String) request.getParameter("category");
		String validationMessage = validateInputData(albumCategory);
		if (validationMessage.equals(REG_SUCC_MSG)) {
			try {
				if (!albumDao.existAlbum(albumCategory, ((User) (request.getSession().getAttribute("user"))).getId())) {
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
				e.printStackTrace();
				return "error500";
			}
		} else {
			request.setAttribute("emptyCategoryField", validationMessage);
			return "createAlbum";
		}

		return "album";
	}

	private String validateInputData(String category) {
		if (category == null || category.isEmpty()) {
			return "Please fill all the required fields!";
		}
		return REG_SUCC_MSG;
	}

}