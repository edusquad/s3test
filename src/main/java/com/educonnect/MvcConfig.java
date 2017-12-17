package com.educonnect;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.educonnect.security.SpringSecurityHelper;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Configuration
	@ConditionalOnClass(SpringSecurityHelper.class)
	static class SpringSecurityHelperAutoConfiguration {

		@Autowired
		private HandlebarsViewResolver handlebarsViewResolver;

		@Autowired
		private SpringSecurityHelper springSecurityHelper;

		@PostConstruct
		public void registerHelper() {
			handlebarsViewResolver.registerHelper(SpringSecurityHelper.NAME, springSecurityHelper);
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

		registry.addViewController("/").setViewName("index");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/signUp").setViewName("signUp");
		registry.addViewController("/login").setViewName("login");

		/**
		 * 
		 */
		registry.addViewController("/member/parentSignUp").setViewName("member/parentSignUp");
		registry.addViewController("/member/parentJoinAuthWaitForm").setViewName("member/parentJoinAuthWaitForm");
		registry.addViewController("/member/parentJoinFormAfterAuth").setViewName("member/parentJoinFormAfterAuth");
		registry.addViewController("/member/parentAdditional").setViewName("member/parentAdditional");
	}
}
