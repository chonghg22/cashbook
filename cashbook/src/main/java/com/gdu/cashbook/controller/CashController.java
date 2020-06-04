package com.gdu.cashbook.controller;


import java.time.LocalDate;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.service.CategoryService;
import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MonthAndPrice;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	
	
	
	//가계부 수정 Form
	@GetMapping("/updateCash")
	public String updateCash(HttpSession session, Model model, @RequestParam(value="cashNo", required = false) int cashNo) {
		//getCashListByDate 보내는 cashNo값이 들어오는지 디버깅
			System.out.println(cashNo + "/cashNo/getUpdateCash");
		//로그인이 되어있지 않으면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		//cashService 내 selectCashOne 메소드를 cash 변수에 담음
		Cash cash = cashService.selectCashOne(cashNo);
		//디버깅
			System.out.println(cash + "/cash/getUpdateCash");
		//cash 변수값을 modifyCash.html로 보냄
		model.addAttribute("cash", cash);		
		//현재 로그인 되어있는 회원의 정보를 loginMember 변수에 담음
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");			
		//디버깅
			System.out.println(loginMember +"/loginMember/getUpdateCash");
		//modifyCash.html로 loginMember 변수값을 보냄
		model.addAttribute("loginMember", loginMember);		
		//cashService 내 selectcategoryName 메소드 값을 category변수에 리스트로 담음
		List<Category> category = cashService.selectCategoryName();
		//디버깅
			System.out.println(category + "/category/getUpdateCash");
		//category 변수값을 modifyCash.html로 보냄
		model.addAttribute("category", category);
		//modifyCash.html 호출
		return "modifyCash";
	}
	
	//가계부 수정 Action
	@PostMapping("/updateCash")
	public String updateCash(HttpSession session, Cash cash) {
		//로그인이 되어있지 않으면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		//가계부 수정 Form에서 입력받은 cash값을 cashService 내 updateCash 메소드로 보냄
		cashService.updateCash(cash);
		//디버깅
			System.out.println(cash + "/cash/postUpdateCash");
		//위 메소드가 정상적으로 완료되면 getCashListByDate.html로 돌아가는데 이때 getCashDate의 값을 day 변수에 담아서 보냄
		return "redirect:/getCashListByDate?day="+cash.getCashDate();
	}
	
	
	//가계부 삭제 Form
	@GetMapping("/deleteCash")
	public String deleteCash(HttpSession session, Cash cash) {
		//로그인이 되어있지 않다면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/index";
		}
		//cashService 내 deleteCash로 cash 변수값 보냄
		cashService.deleteCash(cash);
		//디버깅
			System.out.println(cash + "/cash/getDeleteCash");
		//메소드가 정상적으로 완료되면 getCashListByDate.html로 돌아감
		return "redirect:/getCashListByDate";
	}
	
	//가계부 추가 Form
	@GetMapping("/addCash")
	public String addCash(Model model, HttpSession session) {
		//로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		//현재 로그인 되어있는 회원정보값을 loginMember 변수에 담음
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
		//디버깅
			System.out.println(loginMember + "/loginMember/getAddCash");
		//loginMember 변수 값을 addCash.html로 보냄 
		model.addAttribute("loginMember", loginMember);
		//cashService 내 selectCategoryName 메소드를 category변수에 리스트로 저장
		List<Category> category = cashService.selectCategoryName();
		//디버깅
			System.out.println(category + "/category/getAddCash");
		//category 변수값을 addCash.html로 보냄 
		model.addAttribute("category", category);		
		//addCash.html 호출
		return "addCash";
	}
	
	//가계부 추가 Action
	@PostMapping("/addCash")
	public String addCash(HttpSession session, Cash cash) {
		//로그인이 되어있지 않다면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember")==null) {								
			return "redirect:/index";									
		}
		//가계부 추가 Form에서 받은 cash값을 cashService 내 addCash메소드로 넘김
		cashService.addCash(cash);
		//디버깅
			System.out.println(cash + "/cash/postAddCash");
		//위 메소드가 정상적으로 완료되면 getCashListByDate로 돌아가는데 getCashDate의 값을 day 변수에 넣어서 보냄 
		return "redirect:/getCashListByDate?day ="+cash.getCashDate();
	}
	
	//가계부 월별 비교 Form
	@GetMapping("/getCashListByMonthCompare")
	public String getCashListByMonthCompare(HttpSession session, Model model, Cash cash, LocalDate day) {
		//Calendar.getInstance는 객체를 하나만 만들고 공유해서 사용한다.
		Calendar mDay = Calendar.getInstance();
		//로그인이 되어있지 않으면 index로 돌려보내는 조건문
		if(session.getAttribute("loginMember")==null) {
			return "redirect:/"; 
		}
		//day 변수값이 null이면 로컬데이트를 기입한고, 그게 아니면 형변환한다.
		if(day == null) {
			day = LocalDate.now();
		}else {
			mDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());
		}
		//현재 로그인 되어있는 회원의 id를 memberId 변수에 기입한다.
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		//디버깅
			System.out.println(memberId);
		//캘린더 내 연도값을 year변수값에 넣는다.
		int year = mDay.get(Calendar.YEAR);
		//디버깅
			System.out.println(year);
		//cashService 내 selectImportmonthAndPriceList메소드를 importmonthAndPriceList 변수에 리스트로 담는다.
		List<MonthAndPrice> importmonthAndPriceList = cashService.selectImportmonthAndPriceList(memberId, year);
		//반복문 작동 테스트
		for(MonthAndPrice mp : importmonthAndPriceList) {
			System.out.println( mp);
		}
		//getCashListByMonthCompare.html로 importmonthAndPriceList변수값을 보낸다.
		model.addAttribute("importmonthAndPriceList", importmonthAndPriceList);
		//cashService 내 selectExpensemonthAndPriceList메소드를 expensemonthAndPriceList 변수에 리스트로 담는다.
		List<MonthAndPrice> expensemonthAndPriceList = cashService.selectExpensemonthAndPriceList(memberId, year);
		//조건문 작동 테스트
		for(MonthAndPrice ep : expensemonthAndPriceList) {
			System.out.println(ep);
		}
		//getCashListByMonthCompare.html로 expensemonthAndPriceList변수값을 보낸다.
		model.addAttribute("expensemonthAndPriceList", expensemonthAndPriceList);
		//getCashListByMonthCompare.html 호출
		return "getCashListByMonthCompare";
	}
	
	//월별 가계부 Form
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {		 
		//Calendar.getInstance는 객체를 하나만 만들고 공유해서 사용한다.
		Calendar cDay = Calendar.getInstance();
		//디버깅
			System.out.println(cDay + "/cDay/getCashListByMonth/Controller/Get");
		//로그인이 되어있지 않으면 index.html로 돌려보내는 조건문
		if(session.getAttribute("loginMember")==null) {								
			return "redirect:/index";											
		}
		//day 변수값이 null이면 day에 현재지역시간을 기입하는 조건문
		if(day == null) {															
			day = LocalDate.now();
		//디버깅
			System.out.println(day + "/day/getCashListByMonth/Controller/Get");	
		//day 변수값이 null이 아니면 localDate의 값을 Calendar로 변환한다 (day를 cDay로 형변환)
		}else {			
			cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());
		//디버깅
				System.out.println(cDay + "/cDay/getCashListByMonth/Controller/Get");
		}
		//현재 로그인 되어있는 회원의 아이디를 memberId 변수에 넣음
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		//디버깅
			System.out.println(memberId + "/memberId/getCashListByMonth/Controller/Get");
		//캘린더에서 연도값을 가져와 year변수에 기입
		int year = cDay.get(Calendar.YEAR);
		//디버깅
			System.out.println(year + "/year/getCashListByMonth/Controller/Get");
		//캘린더에서 매 달의 값을 가져오는데 0부터 시작해서 1을 추가함 그리고 month 변수에 기입
		int month = cDay.get(Calendar.MONTH)+1;							
		//디버깅
			System.out.println(month + "/month/getCashListByMonth/Controller/Get");
		//cashService 내 getCashAndPriceList 메소드를 가져와 dayAndPriceList 변수에 리스트로 기입
		List<DayAndPrice> dayAndPriceList = cashService.getCashAndPriceList(memberId, year, month);
		//디버깅
			System.out.println(dayAndPriceList + "/dayAndPriceList/getCashListByMonth/Controller/Get");
		//반복문 선언 후 작동되는지 확인
		for(DayAndPrice dp : dayAndPriceList) {									
			System.out.println(dp +"/dp/getCashListByMonth/Controller/Getr");							
		} 
		//cDay의 값을 firstDay변수에 기입
		Calendar firstDay = cDay;	
		//첫번째날을  1일로 지정하고 firstDay 변수에 기입
		firstDay.set(Calendar.DATE, 1);	
		//특정 날짜  0 일요일 1 월요일 2 화요일 ....6 토요일 
 		firstDay.get(Calendar.DAY_OF_WEEK);
 		//디버깅
 			System.out.println(firstDay + "/firstDay/getCashListByMonth/Controller/Get");		
		//일자를 getCashListByMonth.html로 보냄
 		model.addAttribute("day", day);												
		//월별을 getCashListByMonth.html로 보냄
 		model.addAttribute("month", cDay.get(Calendar.MONTH)+1); 					
		//매 달 마지막 날짜를 getCashListByMonth.html로 보냄
 		model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE)); 		
		//지출과 수입의 합을 getCashListByMonth.html로 보냄
 		model.addAttribute("dayAndPriceList", dayAndPriceList); 					
		//매달 1일의 위치를 getCashListByMonth.html로 보냄
 		model.addAttribute("firstDayofWeek", firstDay.get(Calendar.DAY_OF_WEEK));  	
		//getCashListByMonth.html 호출
		return "getCashListByMonth";
	}
	
	//일별 가계부 Form
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		//getCashListByDate.html에서 보내는 day값 잘 넘어오는지 디버깅
		System.out.println(day + "/day/getCashListByDate");
		//day값이 null이면 day변수에 현재 지역시간을 넣음 
		if(day == null) {
			day = LocalDate.now(); 
		}
		//디버깅
			System.out.println(day + "/day/getCashListByDate");
		//로그인이 되어있지 않다면 index로 돌아가는 조건문
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}	
		//현재 로그인 되어있는 회원의 아이디값을 가져와서 loginMemberId변수에 값을 넣음
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		//디버깅
			System.out.println(loginMemberId + "/loginMemberId/getCashListByDate");		
		//객체 선언 및 cash변수에 day값이랑 loginMemberId값을 담음
		Cash cash = new Cash(); // + id , + date ("yyyy-mm-dd")
		cash.setCashDate(day.toString());
		cash.setMemberId(loginMemberId);
		//디버깅
			System.out.println(cash + "/cash/getCashListByDate/Controller/Get");
		//cashService 내 getCashListByDate의 값을 map 변수에 담음
		Map<String, Object> map = cashService.getCashListByDate(cash);
		//디버깅	
			System.out.println(map + "/map/getCashListByDate");		
		//map에 담긴 cashList와 cashKindSum 그리고 day값을 getCashListByDate.html로 보냄
		model.addAttribute("cashList", map.get("cashList"));
		model.addAttribute("day", day);
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		//getCashListByDate.html 호출
		
		return "getCashListByDate";
	}
}
