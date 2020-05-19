package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
@Mapper
public interface CashMapper {
	public List<Cash> selectCashListByToday(Cash cash);
		
	public int selectCashKindSum(Cash cash);
}