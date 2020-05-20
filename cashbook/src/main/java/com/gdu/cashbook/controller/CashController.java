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
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {		 
		Calendar cDay = Calendar.getInstance();										//Calendar.getInstance는 객체를 하나만 만들고 공유해서 사용한다.
		System.out.println(cDay + "/cDay/CashController");							//디버깅		
		if(session.getAttribute("loginMember")==null) {								//만약 로그인이 되어있지 않다면
			return "redirect:/index";												//index로 돌려보낸다.
		}
		if(day == null) {															//만약 day가 null이라면
			day = LocalDate.now();													//day 변수에 현재시간을 기입한다.
			System.out.println(day + "/day/CashController");						//디버깅
			
		}else {
			/*
			 * LocalDate -> Calendar
			 * LocalDate -> Date -> Calendar
			 * LocalDate -> String -> Calendar
			 * LocalDate -> Calendar
			 */
			cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth());  //localDate의 값을 Calendar로 변환 (day 를 cDay로 형변환)
			System.out.println(cDay + "/cDay/CashController");
		}
		String memberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();//현재 로그인 되어있는 회원의 정보를 가져와 기입 		
		System.out.println(memberId + "/memberId/CashController");					//디버깅
		int year = cDay.get(Calendar.YEAR);											//연도의 값을 가져와 year 변수에 기입 
		System.out.println(year + "/year/CashController");							//디버깅
		int month = cDay.get(Calendar.MONTH)+1;										//매 달의 값을 가져오는데 0부터 시작하기 때문에 1을 추가하고 month 변수에 기입 
		System.out.println(month + "/month/CashController");						//디버깅
		List<DayAndPrice> dayAndPriceList = cashService.getCashAndPriceList(memberId, year, month);//객체 선언 
		System.out.println(dayAndPriceList + "/dayAndPriceList/CashController");	//디버깅
		for(DayAndPrice dp : dayAndPriceList) {										//반복문 
			System.out.println(dp +"/dp/CashController");							//디버깅
		}
		/* 0. 금일 LocalDate 타입
		 * 1. 금일 Calendar 타입 
		 * 2. 이번달의 마지막 일
		 * 3. 이번달 1일의 요일 
		 * 세가지를 폼으로 보내줄꺼임 
		 */
		Calendar firstDay = cDay;													//cDay의 값을 firstDay변수에 기입
		firstDay.set(Calendar.DATE, 1);												//첫번째날을  1일로 변경
 		firstDay.get(Calendar.DAY_OF_WEEK);											//특정 날짜  0 일요일 1 월요일 2 화요일 ....6 토요일 
		System.out.println(firstDay + "/firstDay/CashController");					//디버깅
		model.addAttribute("day", day);												//일
		model.addAttribute("month", cDay.get(Calendar.MONTH)+1); 					//월
		model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE)); 		//마지막 일
		model.addAttribute("dayAndPriceList", dayAndPriceList); 					//지출과 수입의 합
		model.addAttribute("firstDayofWeek", firstDay.get(Calendar.DAY_OF_WEEK));   //매달 1일의 위치 선정	
		return "getCashListByMonth";
	}
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session, Model model, @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
		if(day == null) {
			day = LocalDate.now(); 
		}
		System.out.println(day + "<--day");
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		//로그인 아이디
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		/*
		//오늘 날짜
		Date day = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strToday = sdf.format(day);
		
		System.out.println(strToday + "/strToday/CashController");
		*/
		
		Cash cash = new Cash(); // + id , + date ("yyyy-mm-dd")
		cash.setCashDate(day);
		cash.setMemberId(loginMemberId);
		
		Map<String, Object> map = cashService.getCashListByDate(cash);
		model.addAttribute("cashList", map.get("cashList"));
		model.addAttribute("day", day);
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		System.out.println(map.get("cashKindSum"));
		
		return "getCashListByDate";
	}
}
