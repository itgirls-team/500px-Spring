package com.fp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fp.dbModel.DbManager;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

	@Autowired
	private DbManager manager;

	@RequestMapping(value = "/addComment", method = RequestMethod.PUT)
	@ResponseBody
	public void addComment(HttpServletRequest request, HttpServletResponse response) {

		response.setStatus(200);
	}

}
