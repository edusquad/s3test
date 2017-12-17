package com.educonnect.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public CustomLoginSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session != null) {
			String redirectUrl = (String) session.getAttribute("prevPage");
			if (redirectUrl != null) {
				session.removeAttribute("prevPage");
				getRedirectStrategy().sendRedirect(req, res, redirectUrl);
			} else {
				super.onAuthenticationSuccess(req, res, auth);
			}
		} else {
			super.onAuthenticationSuccess(req, res, auth);
		}
	}
}
