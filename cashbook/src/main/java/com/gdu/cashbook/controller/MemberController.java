  
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
	
	// 회원 비밀번호 찾기
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}
	
	@PostMapping("/findMemberPw")
	public String findMemberPw(Model model, Member member, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		int row = memberService.getMemberPw(member);
		System.out.println(row + "/row/MemberController");
		String msg = "아이디와 이메일을 확인하세요.";
		if(row == 1) {
			msg = "비밀번호를 입력한 이메일로 전송했습니다."; 
		}
		model.addAttribute("msg", msg);
		return "findMemberPwResult";
	}
	// 회원 아이디 찾기
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberId";
	}
	
	@PostMapping("/findMemberId")
	public String findMemberId(Model model, Member member, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String findMemberId = memberService.getMemberIdByMember(member);
		System.out.println(findMemberId + "/findMemberId/MemberController");
		model.addAttribute("findMemberId", findMemberId);
		return "findMemberIdResult";
	}
	//회원정보 수정
	@GetMapping("/modifyMemberInfo")
	public String modifyMember(Model model, LoginMember loginMember, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		Member member = memberService.getModifyMemberOne(loginMember);
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("member", member);
		return "memberModify";
	}
	@PostMapping("/modifyMemberInfo")
	public String modifyMember(Model model, MemberForm memberForm, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		System.out.println(memberForm.getMemberAddress());
		System.out.println(memberForm.getMemberName());
		System.out.println(memberForm.getMemberEmail());
		System.out.println(memberForm.getMemberPhone());
		System.out.println(memberForm.getMemberPic());
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		
		
			if(memberForm.getMemberPic().getContentType().equals("image/png") || 
					memberForm.getMemberPic().getContentType().equals("image/jpeg") ||
					memberForm.getMemberPic().getContentType().equals("image/gif")) 
				{ // 입력과 저장타입의 불일치로 service에서 memberForm -> member + 폴더에 파일 저장
					int result = memberService.modifyMemberInfo(memberForm, loginMember);
					System.out.println(result + "<-- result");
					model.addAttribute("loginMember", loginMember);
					return "redirect:/memberInfo";
				}
			memberService.modifyMemberInfo(memberForm, loginMember);
			model.addAttribute("loginMember", loginMember);
			return "redirect:/memberInfo";
		}
	
	
	// 회원탈퇴
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session, LoginMember loginMember) {
		if(session.getAttribute("loginMember") == null) {								//로그인이 되어있지 않다면
			return "redirect:/index";													//index로 돌아감
		}
		return "removeMember"; 															//removeMember.html로 리턴
	}
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		if(session.getAttribute("loginMember") == null) {								//로그인이 되어있지 않다면
			return "redirect:/index";													//index로 돌려보냄
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember"); 
		loginMember.setMemberPw(memberPw); 												//removeMember.html에서 입력한 비밀번호로 변경
		System.out.println(loginMember.getMemberId());									//디버깅
		System.out.println(loginMember.getMemberPw());									//디버깅
		memberService.removeMember(loginMember); 										// 삭제 메서드 호출
		session.invalidate();															//세션 종료
		return "redirect:/";															//index로 돌려보낸다.
	}
	
	// 회원정보
	@GetMapping("/memberInfo")
	public String memberInfo(Model model, HttpSession session) {
		if(session.getAttribute("loginMember") == null) {								//로그인이 되어있는 상태가 아니라면 
			return "redirect:/";														//index로 돌려보낸다.
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");		//현재 로그인 되어있는 회원의 정보를 loginMember에 담는다.
		System.out.println(loginMember + "/loginMember/MemberController");				//디버깅
		Member member = memberService.getMemberOne(loginMember);						//memberService에서  로그인 되어있는 회원 정보가 있는 메서드 호출
		System.out.println(member + "/member/MemberController");						//디버깅
		model.addAttribute("member", member);											//member의 값을 "member"의 변수에 담는다.
		model.addAttribute("loginMember", loginMember);									//loginMember의 값을 "loginMember"변수에 담는다.
		return "memberInfo";															//위 모든 정보를 가지고 memberInfo.html로 돌아간다..
	}
	
	@PostMapping("/checkMemberId")
	public String checkMemberId(Model model, @RequestParam("memberIdCk") String memberIdCk, HttpSession session) {
		if(session.getAttribute("loginMember") != null) {								//로그인이 되어있지 않는다면
			return "redirect:/";														//index로 돌려보낸다.
		}
		String confirmMemberId = memberService.checkmemberId(memberIdCk);				//서비스에서 checkmemberId 메서드 호출.(회원가입시 아이디가 중복되는지 안되는지에 관한 정보를 담음)
		System.out.println(confirmMemberId + "<--confirmMemberId");											//디버깅
		if(confirmMemberId == null) {													//중복되는 아이디가 없다면
			model.addAttribute("memberIdCk", memberIdCk);								//memberIdCk 값을 memberIdCk 라는 변수에 담는다.
		} else {																		//그게 아니면
			model.addAttribute("msg", "사용할 수 없습니다.");									//사용할 수 없습니다 라는 메세지를 msg변수에 담는다.
		}
		return "addMember";																//addmember.html로 복귀한다.
	}
	// login GET, POST
	@GetMapping("/login")
	public String login(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {								//로그인이 되어있지 않다면
			return "redirect:/";														//index로 복귀한다.
		} 
		return "login";																	//login.html을 불러온다.
	}
	@PostMapping("/login")
	public String login(Model model, LoginMember loginMember, HttpSession session) {
		LoginMember returnLoginMember = memberService.login(loginMember);				//사용자들의 아이디와 비밀번호 메서드 호출
		System.out.println(returnLoginMember +"/returnLoginMember/MemberController");	//디버깅
		if(returnLoginMember == null) {													//로그인 정보가 null이라면 
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요"); 						//아이디와 비밀번호 확인하세요 라는 메세지를 msg변수에 담는다. 
			return "login";																//로그인 페이지를 불러온다.
		} else {																		//그게 아니면
			session.setAttribute("loginMember", returnLoginMember);						//로그인된 값을 loginMember 변수에 담는다.
			return "redirect:/home";													//home으로 복귀한다.
		}	
	}
	
	// logout GET
	@GetMapping("logout")
	public String logout(HttpSession session) {										
		if(session.getAttribute("loginMember") == null) {							//로그인 한 회원이 없다면 
			return "redirect:/";													//index로 되돌려보낸다.
		}
		session.invalidate();														//세션 종료
		return "redirect:/";														//index로 복귀
	}	
	
	// addMember GET, POST
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {	
		if(session.getAttribute("loginMember") != null) { 							//만약 로그인 된 회원이 아니라면
			return "redirect:/";													//index로 되돌려보낸다.
		}
		return "addMember";															//addMember.html 파일을 불러온다.
	}
	@PostMapping("/addMember")
	public String addMember(HttpSession session, MemberForm memberForm) {
		//System.out.println("controller" + member.toString());
		//System.out.println(memberForm + " <--memberController.addmember memberForm");
		if(session.getAttribute("loginMember") == null) { // 로그인 안되있을때
			MultipartFile mf = memberForm.getMemberPic();
			// 이미지 파일이 입력됐을때
			if(memberForm.getMemberPic() != null && !mf.getOriginalFilename().equals("")) {
				if(!memberForm.getMemberPic().getContentType().equals("image/png") && !memberForm.getMemberPic().getContentType().equals("image/jpeg") && !memberForm.getMemberPic().getContentType().equals("image/gif")) {
					return "redirect:/addMember?imgMsg=n";
				}
			}
			memberService.addMember(memberForm);
		}
		return "redirect:/index";
	}
}