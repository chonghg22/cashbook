package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
@Mapper
public interface CashMapper {
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map );
	public List<Cash> selectCashListByToday(Cash cash);		
	public int selectCashKindSum(Cash cash);
}
