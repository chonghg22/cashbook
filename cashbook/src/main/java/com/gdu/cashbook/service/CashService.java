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
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	public int addCash(Cash cash) {
		Cash c = new Cash();
		c.setMemberId(cash.getMemberId());
		c.setCashKind(cash.getCashKind());
		c.setCategoryName(cash.getCategoryName());
		c.setCashPrice(cash.getCashPrice());
		c.setCashPlace(cash.getCashPlace());
		c.setCashMemo(cash.getCashMemo());
		return cashMapper.insertCash(cash);
	}
	public List<DayAndPrice> getCashAndPriceList(String memberId, int year, int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectDayAndPriceList(map);
	}
	public Map<String, Object> getCashListByDate(Cash cash){
	List<Cash> cashList =  cashMapper.selectCashListByToday(cash);
	int cashKindSum = cashMapper.selectCashKindSum(cash);
	Map<String, Object> map = new HashMap<>();
	map.put("cashList", cashList);
	map.put("cashKindSum", cashKindSum);
	return map;
	}
	
}
