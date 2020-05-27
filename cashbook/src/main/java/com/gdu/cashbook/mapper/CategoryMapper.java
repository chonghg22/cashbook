package com.gdu.cashbook.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
@Mapper
public interface CategoryMapper {
	public List<Category> selectCategoryName();	
	public int addCategory(Category category);
	public int modifyCategory(Category category);
	public Category selectCategoryOne(int categoryNo);
	public int deleteCateogry(Category cateogry);
}
