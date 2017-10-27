package com.fp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fp.config.WebAppInitializer;
import com.fp.model.Album;
import com.fp.model.User;

@Controller
public class CoverController {

	@RequestMapping(value = "/cover", method = RequestMethod.GET)
	public String getAvatar(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String cover = null;
		User user = (User) request.getSession().getAttribute("user");
		Set<Album> albumsOfUser = user.getAlbumsOfUser();
		for (Album album : albumsOfUser) {
			cover = album.getPicture();
			File file = new File(WebAppInitializer.LOCATION + cover);
			try {
				Files.copy(file.toPath(), response.getOutputStream());
			} catch (IOException e) {
				request.setAttribute("error", "problem with the stream. Could not open output stream!");
			}
		}
		return "main";
	}
}
