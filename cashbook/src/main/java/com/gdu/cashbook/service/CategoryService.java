package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Category;

@Service
public class CategoryService {
	@Autowired
	CategoryMapper categoryMapper;
	public List<Category> selectCateogryList(Category category) {
		return  categoryMapper.selectCategoryList(category);
	}
}
