package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	//로그인 폼 (폼을 가져옴)
	@GetMapping("/login") 
	public String loginMember() {
		return "login";
	}
	//로그인 액션 (로그인 실행되는 값을 가져옴) 
	@PostMapping("/login")
	public String loginMember(LoginMember loginMember, HttpSession session) {
		System.out.println(loginMember);
		//memberService에서 로그인된 값을 returnLoginMember 변수로 기입 
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) {
			return "redirect:/login";
		}else {
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/index";
		}
		
	}
	@GetMapping("/logout")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/index";
	}
	
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addmember(Member member) {
		System.out.println(member.toString());
		memberService.insertMember(member);
	          	return "redirect:/index";
	}
}
