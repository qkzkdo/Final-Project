package com.pha.topten.security;

import org.apache.http.protocol.HTTP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	
	//
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
					.antMatchers("/css/**","/js/**","/images/**","/webjars/**","/favicon.ico*").permitAll()
					.antMatchers("/","/signup","test").permitAll()
					.antMatchers("/category/**","/brands/**","/common/**","/prepare","/project/**","/rest-item/**").permitAll()
					.antMatchers("/signup-v").permitAll() //유효성 검사
					.anyRequest().authenticated()
			)
			//로그인
			.formLogin(form -> form
					.loginPage("/login")
					.usernameParameter("email") //default : username
					.passwordParameter("pass") //default: password
					.permitAll()
					
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
			)
			//.csrf(csrf -> csrf.disable()) //jwt 사용한다면 
			;
		return http.build();
	}
	
}