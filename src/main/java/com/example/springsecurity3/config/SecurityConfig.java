package com.example.springsecurity3.config;

import com.example.springsecurity3.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC 빈(bean)을 등록
public class SecurityConfig {

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.headers((headers)->
					headers.contentTypeOptions(contentTypeOptionsConfig ->
							headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)))
				.authorizeRequests(authorize ->
								authorize
						.requestMatchers("/user/**").authenticated()
						// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
						// hasRole('ROLE_USER')")
						// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
						// hasRole('ROLE_USER')")
						.requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll())
		//				.and()
		//				.formLogin()
		//				.loginPage("/login")
		//				.loginProcessingUrl("/loginProc")
		//				.defaultSuccessUrl("/")
		//				.and()
						.oauth2Login(oauth->
								oauth
										.loginPage("/login")
										.userInfoEndpoint(userInfoEndpointConfig ->
												userInfoEndpointConfig
													.userService(principalOauth2UserService)));

		return http.build();
	}
}
