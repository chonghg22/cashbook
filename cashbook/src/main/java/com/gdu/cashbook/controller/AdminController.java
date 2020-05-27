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
	@GetMapping("/memberList")
	public String memberList(HttpSession session, Member member, Model model) {
		 List<Member> list = memberService.getselectMember(member);
		 model.addAttribute("list", list);
		 LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		 model.addAttribute("loginMember", loginMember);
		 return "memberList";
	}
	
	@GetMapping("/updateAdmin")
	public String updateAdmin(HttpSession session, Model model, @RequestParam("memberId") String memberId) {
		System.out.println(memberId + "<----memberId");
		if(session.getAttribute("loginMember") == null) {								//로그인이 되어있는 상태가 아니라면 
			return "redirect:/";														//index로 돌려보낸다.
		}
		adminService.updateAdmin(memberId);
		return "resultadmin";
	}
	@GetMapping("/adminLogin")
	public String adminLogin(HttpSession session) {
		if(session.getAttribute("loginMember")!=null) {
			return "redirect:/index";
		}
		
		return "adminLogin";
	}
	@PostMapping("adminLogin")
	public String adminLogin(HttpSession session, LoginMember loginMember, Model model, Admin admin) {
		String loginAdmin = adminService.loginAdmin(admin);
		
		session.setAttribute("loginAdmin", loginAdmin);
		return "redirect:/adminHome";
	}
	
}
