package com.educonnect.security.controllers;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		String referrer = request.getHeader("Referer");
		request.getSession().setAttribute("prevPage", referrer);
		return "login";
	}

}
