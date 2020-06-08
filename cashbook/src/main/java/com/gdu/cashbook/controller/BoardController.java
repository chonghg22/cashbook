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
	
	//게시판 리스트 Form
	@GetMapping("/boardList")
	public String boardList(Model model, Board board) {
		//boardService 내 selectBaordList 메소드를 list변수에 담음
		List<Board> list = boardService.selectBoardList();
		//디버깅
			System.out.println(list + "/list/getBoardList");
		//list 변수값을 getBoardList.html로 보내줌
		model.addAttribute("list", list);			
		//getBoardList.html 호출
		return "getBoardList";
	
	//게시글 추가 Form
	}
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session, Model model, Board board ) {
		//로그인 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/";
		}
		//현재 로그인 되어있는 회원정보를 가져와서 loginMember 변수에 담음
		LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
		//디버깅
			System.out.println(loginMember + "/loginMember/getAddBoard");
		//loginMember의 값을 addBoard.html로 넘겨줌
		model.addAttribute("loginMember", loginMember);		
		//addBoard.html 호출
		return "addBoard";
	}
	
	//게시글 추가  Action
	@PostMapping("/addBoard")
	public String addBoard(Board board) {
		//게시글 추가 Form에서 입력된 board값을 boardService 내 insertBoard 메소드로 보냄 
		boardService.insertBoard(board);
		//디버깅
			System.out.println(board + "/board/PostAddBoard");
		//위 메소드가 정상적으로 완료되면 getBoardList.html로 돌아감
		return "redirect:/boardList";
	}
	
}
