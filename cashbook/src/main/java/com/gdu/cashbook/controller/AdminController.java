package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.AdminService;
import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Admin;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class AdminController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService adminService;
	


	
	//관리자 로그인 Form
	@GetMapping("/adminLogin")
	public String adminLogin(HttpSession session) {
		//로그인되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/index";
		}
		//adminLogin.html 호출
		return "adminLogin";
	}
	
	
	//관리자 로그인 Action
	@PostMapping("adminLogin")
	public String adminLogin(HttpSession session, LoginMember loginMember, Model model, Admin admin) {
		//adminService 내 loginAdmin 메소드를 loginAdmin 변수에 담음
		String loginAdmin = adminService.loginAdmin(admin);
		//디버깅
			System.out.println(loginAdmin + "/loginAdmin/PostAdminLogin");
		//session으로 로그인 하려는 값을 념겨줌
		session.setAttribute("loginAdmin", loginAdmin);			
		//위 메소드가 정상적으로 실행이 완료되면 adminHome.html으로 돌아감
		return "redirect:/adminHome";
	}
	
}
