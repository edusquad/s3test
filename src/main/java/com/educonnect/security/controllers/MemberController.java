package com.educonnect.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.educonnect.MailSource;
import com.educonnect.domain.user.Token;
import com.educonnect.domain.user.TokenRepository;
import com.educonnect.security.model.Member;
import com.educonnect.security.model.MemberRepository;
import com.educonnect.security.model.MemberRole;
import com.educonnect.utils.MailSender;

import java.util.Arrays;

@Controller
public class MemberController {

	@Autowired
	MemberRepository parentMemberRepository;

	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${property.host}")
	String RECEIVER_MAIL_ADDRESS;
	
	@Value("${property.sender}")
	public String MAIL_SENDER;

	@Value("${property.sender_password}")
	public String MAIL_SENDER_PASSWORD;

	@Value("${property.sender_url}")
	public String DOMAIN;

	
	@PostMapping("/generateToken")
	public String generateToken(@RequestBody Token token) throws Exception {
		if (!parentMemberRepository.findByUid(token.getUemail()).isPresent()) {
			token = new Token(token.getUemail());
			MailSource ms = new MailSource(MAIL_SENDER, MAIL_SENDER_PASSWORD, DOMAIN);
			MailSender joinAuthMail = new MailSender(token.getUemail(),ms);
			tokenRepository.save(token);
			joinAuthMail.sendMailForToken(token);
			return "redirect:/member/parentJoinAuthWaitForm";
		}
		return "redirect:/member/parentSignUp";
	}

	@GetMapping("/api/token")
	public ModelAndView token(String token) {
		System.out.println("Token : " + token);
		Token dbToken = tokenRepository.findByToken(token);
		if (dbToken.getUemail() == null) {
			return new ModelAndView("redirect:/member/parentSignUp");
		}
		ModelAndView mav = new ModelAndView("/member/parentJoinFormAfterAuth");
		mav.addObject("token", dbToken);
		return mav;
	}

	@PostMapping("/parentSignUp")
	public String signUp(Member parentMember) {
		parentMember.setMemberRoles(Arrays.asList(new MemberRole("PARENT")));
		parentMemberRepository.save(parentMember);
		return ("redirect:/");
	}

}
