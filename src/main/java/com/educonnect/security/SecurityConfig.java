package com.educonnect.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import com.educonnect.security.model.SecuritySocialProvider;

@EnableOAuth2Client
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Autowired
	MemberDetailService memberDetailService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomLoginSuccessHandler("/");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("security config ....................");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("PARENT").antMatchers("/**").permitAll().and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class).formLogin().loginPage("/login")
				.loginProcessingUrl("/login").successHandler(successHandler()).failureUrl("/login").permitAll()

		;
		http.csrf().disable();

		http.headers().frameOptions().disable();
		// 세션 무효화
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/");

		http.userDetailsService(memberDetailService);
	}

	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook(), "/login/facebook", SecuritySocialProvider.FACEBOOK));
		filters.add(ssoFilter(kakao(), "/login/kakao", SecuritySocialProvider.KAKAO));
		filters.add(ssoFilter(naver(), "/login/naver", SecuritySocialProvider.NAVER));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path, SecuritySocialProvider provider) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(template);
		UserTokenService tokenServices = new UserTokenService(client, provider);
		tokenServices.setRestTemplate(template);
		filter.setApplicationEventPublisher(applicationEventPublisher);
		filter.setTokenServices(tokenServices);
		return filter;
	}

	@Bean
	@ConfigurationProperties("facebook")
	public ClientResources facebook() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("kakao")
	public ClientResources kakao() {
		return new ClientResources();
	}

	@Bean
	@ConfigurationProperties("naver")
	public ClientResources naver() {
		return new ClientResources();
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
}
