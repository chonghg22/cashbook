package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	//index와 비슷한 역활이지만 간단한 구조이기 때문에 index 컨트롤러에 같이 만듬
	@GetMapping("/home")
	public String home(HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		return "home";
	}
}
