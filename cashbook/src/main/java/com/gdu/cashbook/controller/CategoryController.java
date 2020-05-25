package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gdu.cashbook.service.CategoryService;
import com.gdu.cashbook.vo.Category;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	}

 