  
package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	// 회원 비밀번호 찾기 Form
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		//로그인 되어있는 회원이면 index로 보내는 조건문
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//findMemberPw.html 호출
		return "findMemberPw";
	}
	
	//회원 비밀번호 찾기 Action
	@PostMapping("/findMemberPw")
	public String findMemberPw(Model model, Member member, HttpSession session) {
		//로그인이 되어있는 회원이면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//memberService 내 getMemberPw메소드를 row 변수에 담는다.
		int row = memberService.getMemberPw(member);
		//디버깅
			System.out.println(row + "/row/postFindMemberPw");
		String msg = "아이디와 이메일을 확인하세요.";
		//row값이 1이면 msg변수에  하단문구 기입
		if(row == 1) {
			msg = "비밀번호를 입력한 이메일로 전송했습니다."; 
		}
		//findMemberPwResult.html로 메세지 전송
		model.addAttribute("msg", msg);
		//findMemberPwResult.html 호출
		return "findMemberPwResult";
	}
	// 회원 아이디 찾기 Form
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		//로그인이 되어있는 회원이면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//infMemberId.html을 호출
		return "findMemberId";
	}
	//회원 아이디찾기 Action
	@PostMapping("/findMemberId")
	public String findMemberId(Model model, Member member, HttpSession session) {
		//로그인이 되어있는 회원이면 index로 돌아간다.
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//memberService 내 getMemberIdByMember 메소드를 findMemberId 변수에 담음
		String findMemberId = memberService.getMemberIdByMember(member);
		//디버깅
			System.out.println(findMemberId + "/findMemberId/postFindMemberId");
		model.addAttribute("findMemberId", findMemberId);
		return "findMemberIdResult";
	}
	//회원정보 수정 Form
	@GetMapping("/modifyMemberInfo")
	public String modifyMember(Model model, LoginMember loginMember, HttpSession session) {
		//로그인이 되어있지 않으면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		//memberService 내 getModifyMemberOne 메소드를 member 변수에 담음
		Member member = memberService.getModifyMemberOne(loginMember);
		//디버깅
			System.out.println(member + "/member/getModifyMemberInfo");
		//modifyMember.html로  loginMember, member 값을 보냄 
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("member", member);
		//modifyMember.html 호출
		return "modifyMember";
	}
	//회원정보수정 Action
	@PostMapping("/modifyMemberInfo")
	public String modifyMember(Model model, MemberForm memberForm, HttpSession session) {
		//로그인한 회원이 아니라면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		//디버깅
		System.out.println(memberForm.getMemberAddress());
		System.out.println(memberForm.getMemberName());
		System.out.println(memberForm.getMemberEmail());
		System.out.println(memberForm.getMemberPhone());
		System.out.println(memberForm.getMemberPic());
		//현재 로그인 되어있는 회원정보를 loginMember 변수에 담는다.
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");		
		//회원정보 수정시 png 또는 jpeg 또는 gif 확장명을 가진 파일만 넣을 수 있는 조건문
		if(memberForm.getMemberPic().getContentType().equals("image/png") || 
				memberForm.getMemberPic().getContentType().equals("image/jpeg") ||
				memberForm.getMemberPic().getContentType().equals("image/gif")) 
				{ 
				// 입력과 저장타입의 불일치로 service에서 memberForm -> member + 폴더에 파일 저장
				int result = memberService.modifyMemberInfo(memberForm, loginMember);
				//디버깅
					System.out.println(result + "/result/postModifyMemberInfo");
				//loginMember의 변수값을 memberInfo.html로 보냄
				model.addAttribute("loginMember", loginMember);
				//위 조건이 만족되면 memberInfo로 돌아감
				return "redirect:/memberInfo";
			}
			//회원정보 Form에서 받은 변수값을 memberService 내 modifyMemberInfo로 전달
			memberService.modifyMemberInfo(memberForm, loginMember);
			//loginMember 변수값을 memberInfo.html로 전송
			model.addAttribute("loginMember", loginMember);
			//위 조건문이 완성되면 memberInfo.html로 이동
			return "redirect:/memberInfo";
		}
	
	
	//회원탈퇴 Form
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {
		//로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) {								
			return "redirect:/index";													
		}
		//removeMember.html 호출
		return "removeMember"; 															
	}
	
	//회원탈퇴 Action
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		//로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) {								
			return "redirect:/index";												
		}
		//로그인이 되어있는 회원의 정보를 loginMember 변수에 담는다.
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember"); 
		//회원탈퇴 Form에서 가져온 memberPw를 loginMember 변수에 담음
		loginMember.setMemberPw(memberPw); 												
		//디버깅
			System.out.println(loginMember.getMemberId());									
			System.out.println(loginMember.getMemberPw());									
		//memberService 내 removeMember 메소드로 loginMember값을 넘김
		memberService.removeMember(loginMember); 										
		//세션 종료
		session.invalidate();
		//메소드가 정상적으로 실행완료되면 index로 돌아감
		return "redirect:/";															
	}
	
	// 회원정보보기 Form
	@GetMapping("/memberInfo")
	public String memberInfo(Model model, HttpSession session) {
		//로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) {								
			return "redirect:/";													
		}
		//로그인이 되어있는 회원의 정보를 loginMember 변수에 담는다.
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");		
		//디버깅
			System.out.println(loginMember + "/loginMember/MemberController");				
		//memberService 내 getMemberOne 메소드를 member변수에 담는다.
		Member member = memberService.getMemberOne(loginMember);
		//디버깅
			System.out.println(member + "/member/MemberController");	
		//memberInfo.html로 member, loginMember 변수값을 보냄
		model.addAttribute("member", member);										
		model.addAttribute("loginMember", loginMember);	
		//memberInfo.html 호출
		return "memberInfo";														
	}
	
	//아이디 중복확인 Action
	@PostMapping("/checkMemberId")
	public String checkMemberId(Model model, @RequestParam("memberIdCk") String memberIdCk, HttpSession session) {
		//로그인이 되어있으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") != null) {								//로그인이 되어있지 않는다면
			return "redirect:/";														//index로 돌려보낸다.
		}
		//memberService에서 checkmemberId 메서드 호출 후 confirmMemberId 변수에 담음(회원가입시 아이디가 중복되는지 안되는지에 관한 정보를 담음)
		String confirmMemberId = memberService.checkmemberId(memberIdCk);				
		//디버깅
			System.out.println(confirmMemberId + "<--confirmMemberId");										
		//중복되는 아이디가 없다면 중복확인된 아이디를 addMember.html로 보냄 그게 아니라면 사용할 수 없다는 문구를 보냄
		if(confirmMemberId == null) {													
			model.addAttribute("memberIdCk", memberIdCk);								
		} else {																		
			model.addAttribute("msg", "사용할 수 없습니다.");									
		}
		//addMember.html 호출
		return "addMember";																
	}
	
	// 로그인 Form
	@GetMapping("/login")
	public String login(HttpSession session) {
		//로그인이 되어있으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") != null) {							
			return "redirect:/";														
		}
		//login.html 호출
		return "login";																	
	}
	//로그인 Action
	@PostMapping("/login")
	public String login(Model model, LoginMember loginMember, HttpSession session) {
		//memberService 내 login 메소드를 returnLoginMember 에 담음 
		LoginMember returnLoginMember = memberService.login(loginMember);				
		//디버깅
			System.out.println(returnLoginMember +"/returnLoginMember/MemberController");	
		//아이디와 비밀번호가 맞지 않으면 아이디와 비밀번호를 확인하라는 문구를 출력하는 조건문 그게 아니라면 index로 이동
		if(returnLoginMember == null) {												
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요"); 					
			return "login";																
		} else {																
			session.setAttribute("loginMember", returnLoginMember);						
			return "redirect:/index";													
		}	
	}
	
	// 로그아웃 Action
	@GetMapping("/logout")
	public String logout(HttpSession session) {										
		//로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) {							
			return "redirect:/";												
		}
		//세션 종료
		session.invalidate();		
		//메소드가 작동 완료하면 index로 복귀
		return "redirect:/";														
	}	
	
	// 회원가입 Form
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {	
		//로그인이 되어있으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") != null) { 							
			return "redirect:/";												
		}
		//addMember.html 호출
		return "addMember";															
	}
	//회원가입 Action
	@PostMapping("/addMember")
	public String addMember(HttpSession session, MemberForm memberForm) {
		//로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) { 
			MultipartFile mf = memberForm.getMemberPic();
			// 이미지 파일이 입력됐을때
			if(memberForm.getMemberPic() != null && !mf.getOriginalFilename().equals("")) {
				//png, jpeg, gif 확장명을 가진 사진만 넣을 수 있음 
				if(!memberForm.getMemberPic().getContentType().equals("image/png") && !memberForm.getMemberPic().getContentType().equals("image/jpeg") && !memberForm.getMemberPic().getContentType().equals("image/gif")) {
					return "redirect:/addMember?imgMsg=n";
				}
			}
			//회원가입 Form에서 받은 변수값을 memberService 내 addMember 로 보냄
			memberService.addMember(memberForm);
		}
		//메소드가 정상적으로 실행되면 index로 복귀
		return "redirect:/index";
	}
}