package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MonthAndPrice;

@Controller
public class IndexController {
	@Autowired
	private CashService cashService;
	//index Form
	@GetMapping("/index")
	public String index(HttpSession session, Model model,  LocalDate day) {
		//Calendar.getInstance는 객체를 하나만 만들고 공유해서 사용한다.
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
				Cash cash = new Cash(); // + id , + date ("yyyy-mm-dd")
				cash.setCashDate(day.toString());
				cash.setMemberId(memberId);
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
				//캘린더에서 매 달의 값을 가져오는데 0부터 시작해서 1을 추가함 그리고 month 변수에 기입
				int month = mDay.get(Calendar.MONTH)+1;
				model.addAttribute("month", month);
				Map<String, Object> map = cashService.selectImportDayAndPrice(cash);
				model.addAttribute("cashList", map.get("cashList"));
				model.addAttribute("day", day);
				model.addAttribute("importSum", map.get("importSum"));
				model.addAttribute("expenseSum", map.get("expenseSum"));
				return "index";
	}
	
	
	//관리자 Home Form
	@GetMapping("/adminHome")
	public String adminHome(HttpSession session) {
		//관리자로 로그인이 되어있지 않으면 index로 돌아가는 조건문
		if(session.getAttribute("loginAdmin")==null) {			
			return "redirect:/index";
		}
		//adminhome.html 호출
		return "adminhome";
	}
	
}
	
	
