package com.fp.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fp.config.WebAppInitializer;

public class CommonUtils {

	public static boolean isValidString(String string) {
		return string != null && !string.isEmpty();
	}

	public static void showPicture(String picture, HttpServletResponse response, HttpServletRequest request) {
		if (picture == null || picture.isEmpty()) {
			// TODO handle
			return;
		}

		File file = new File(WebAppInitializer.LOCATION + picture);
		try {
			OutputStream out = response.getOutputStream();
			Files.copy(file.toPath(), out);
		} catch (IOException e) {
			request.setAttribute("error", "problem with the stream. Could not open output stream!");
		}
	}
}
