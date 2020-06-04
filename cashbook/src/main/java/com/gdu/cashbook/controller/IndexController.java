package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class IndexController {
	//index Form
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	//index와 비슷한 역활이지만 간단한 구조이기 때문에 index 컨트롤러에 같이 만듬
	//관리자 Home Form
	@GetMapping("/adminHome")
	public String adminHome(HttpSession session) {
		//관리자로 로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginAdmin")==null) {			
			return "redirect:/index";
		}
		//adminhome.html 호출
		return "adminhome";
	}
}
	
	
