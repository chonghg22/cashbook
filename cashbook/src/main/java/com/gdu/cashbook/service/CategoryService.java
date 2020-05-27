package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	public int addCategory(Category category) {
		return categoryMapper.addCategory(category);
	}
	public List<Category> selectCategoryName(){
		return categoryMapper.selectCategoryName();
	}
	public int modifyCategory(Category category) {
		return categoryMapper.modifyCategory(category);
	}
	public Category selectCategoryOne(int categoryNo) {
		return categoryMapper.selectCategoryOne(categoryNo);
	}
	public int deleteCategory(Category category) {
		return categoryMapper.deleteCateogry(category);
	}
}
