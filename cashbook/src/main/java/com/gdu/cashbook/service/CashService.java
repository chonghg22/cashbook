package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.MonthAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	
	public List<MonthAndPrice> selectExpensemonthAndPriceList(String memberId, int year){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		return cashMapper.selectExpensemonthAndPriceList(map);
	}
	
	public List<MonthAndPrice> selectImportmonthAndPriceList(String memberId, int year) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		return cashMapper.selectImportmonthAndPriceList(map);
	}
	
	public Cash selectCashOne(int cashNo) {			//CashController에서 받은 cashNo 매개변수값을 cashMapper의 selectCashOne 메서드로 보내고 그 결과값을 다시 Cash로 담는다.
		return cashMapper.selectCashOne(cashNo);
	}
	public List<Category> selectCategoryName(){
		return cashMapper.selectCategoryName();
	}
	public int deleteCash(Cash cash) {				
		return cashMapper.deleteCash(cash);
	}
	public int addCash(Cash cash) {
		return cashMapper.insertCash(cash);
	}
	public int updateCash(Cash cash) {
		return cashMapper.updateCash(cash);
	}
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year, int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
			System.out.println(map + "/map1/CashService");
		return cashMapper.selectDayAndPriceList(map);
	}
	public Map<String, Object> selectImportDayAndPrice(Cash cash){
		List<Cash> cashList = cashMapper.selectCashListByToday(cash);
		int importSum = cashMapper.selectImportDayAndPriceList(cash);
		int expenseSum = cashMapper.selectExpenseDayAndPriceList(cash);
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("importSum", importSum);
		map.put("expenseSum", expenseSum);
		return map;
	}
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> cashList =  cashMapper.selectCashListByToday(cash);
			System.out.println(cashList + "/cashList/CashService");
		int cashKindSum = cashMapper.selectCashKindSum(cash);
			System.out.println(cashKindSum + "/cashKindSum/CashService");
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
			System.out.println(map + "/map2/CashService");
	return map;
	}
	
}
