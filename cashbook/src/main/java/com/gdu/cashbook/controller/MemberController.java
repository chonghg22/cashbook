package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
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
