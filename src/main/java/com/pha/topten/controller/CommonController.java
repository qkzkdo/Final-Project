package com.pha.topten.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
	
	//로그인 페이지 이동
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	//회원가입 페이지 이동
	@GetMapping("/signup")
	public String signup() {
		return "user/signup";
	}
	
	//회원가입 페이지 이동 유효성검사
	@GetMapping("/signup-v")
	public String signupv(){
		return "user/signup-v";
	}
	
	//준비중 페이지
	@GetMapping("/prepare")
	public String prepare() {
		return "common/prepare";
	}
	


}
