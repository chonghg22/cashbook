package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	@GetMapping("/modifyMemberInfo")
	public String modifyMember(Model model, LoginMember loginMember, HttpSession session) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/index";
		}
		Member member = memberService.getModifyMemberOne(loginMember);
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("member", member);
		return "memberModify";
	}
	@PostMapping("/modifyMemberInfo")
	public String modifyMember(Model model, Member member, HttpSession session) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		String modifyPwCk = memberService.modifyPwCk(member.getMemberPw());
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		int modifyMember = 0;
		if(modifyPwCk !=null) {
			modifyMember = memberService.modifyMemberInfo(member);
		}else {
			model.addAttribute("loginMember", loginMember);
			return "memberInfo";
		}
		if(modifyMember == 1) {
			System.out.println("수정성공");
		}else {
			System.out.println("수정실패");
		}
		return "redirect:/memberInfo";
	}
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {
		if(session.getAttribute("loginMember")==null) {
			return "/redirect:/index";
		}
		return "removeMember";
	}
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		loginMember.setMemberPw(memberPw);
		memberService.removeMember(loginMember);
		session.invalidate();
		return "redirect:/index";
	}
	
	@GetMapping("/memberInfo")
	public String memberInfo(Model model, HttpSession session) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/index";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		Member member = memberService.getMemberOne(loginMember);
		model.addAttribute("member", member);
		model.addAttribute("loginMember", loginMember);
		return "memberInfo";
	}
	
	@PostMapping("/checkMemberId")
	public String checkMemberId(Model model, HttpSession session, @RequestParam("memberIdCheck") String memberIdCheck) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/index";	
		}
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		if (confirmMemberId == null) {
			System.out.println("아이디를 사용할 수 있습니다."); 
			model.addAttribute("memberIdCheck", memberIdCheck);
		}else{
			System.out.println("아이디를 사용할 수 없습니다.");
			model.addAttribute("msg", "사용중인 아이디입니다.");
		}
		return "addMember";
	}
	//로그인 폼 (폼을 가져옴)
	@GetMapping("/login") 
		public String loginMember(HttpSession session) {
			if(session.getAttribute("loginMember") != null) {
			return "redirect:/index";	
			}
			return "login";
			
		}
	
	//로그인 액션 (로그인 실행되는 값을 가져옴) 
	@PostMapping("/login")
	public String loginMember(Model model, LoginMember loginMember, HttpSession session) {
		System.out.println(loginMember);
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/index";	
		}
		//memberService에서 로그인된 값을 returnLoginMember 변수로 기입 
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) {
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "login";
		}else {
			session.setAttribute("loginMember", returnLoginMember);
			return "home";
		}
		
	}
	@GetMapping("/logout")
	public String logoutMember(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			session.invalidate();
			return "redirect:/index";	
			}
		
		return "redirect:/index";
	}
	
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/index";	
			}
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addmember(Member member, HttpSession session) {
		System.out.println(member.toString());
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/index";	
			}
		memberService.insertMember(member);
	          	return "redirect:/index";
	}
	
}