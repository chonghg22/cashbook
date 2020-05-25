package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Category;

@Mapper
public interface CategoryMapper {
	public List<Category> selectCategoryList(Category category);
}
