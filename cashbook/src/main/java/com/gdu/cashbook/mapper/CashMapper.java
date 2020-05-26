package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndPrice;
@Mapper
public interface CashMapper {
	public List<DayAndPrice> selectDayAndPriceList(Map<String, Object> map );	//특정날짜와금액을 가져옴
	public List<Cash> selectCashListByToday(Cash cash);							//특정날짜의 가계부를 가져옴
	public int selectCashKindSum(Cash cash);									//특정날짜의 수입과 지출 총합을 가져옴
	public int insertCash(Cash cash);											//가계부 추가
	public List<Category> selectCategoryName();									//카테고리 리스트 가져옴
	public int deleteCash(Cash cash);											//가계부 삭제
	public int updateCash(Cash cash);											//가계부 수정
	public Cash selectCashOne(int cashNo); 										//cashNo값을 기준으로 그에 해당하는 리스트 가져옴
}
