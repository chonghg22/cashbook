package com.gdu.cashbook.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@GetMapping("/boardList")
	public String boardList(Model model, Board board) {
		List<Board> list = boardService.selectBoardList();
		System.out.println(list + "/list");
		model.addAttribute("list", list);
		return "boardList";
	}
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session, Model model, Board board ) {
		if(session.getAttribute("loginMember")==null) {
			   return "redirect:/";
		}
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		System.out.println(loginMember + "/loginMember/??");
		model.addAttribute("loginMember", loginMember);
		return "addBoard";
	}
	@PostMapping("/addBoard")
	public String addBoard(Board board) {
		boardService.insertBoard(board);
		return "redirect:/boardList";
	}
	
}
