package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CategoryService;
import com.gdu.cashbook.vo.Category;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@GetMapping("/addCategory")
	public String insertCategory(HttpSession session) {
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		return "addCategory";
	}
	@PostMapping("/addCategory")
	public String insertCategory(HttpSession session, Category category) {
		categoryService.addCategory(category);
		return "redirect:/getCategoryList";
	}
	@GetMapping("/getCategoryList")
	public String getCategoryList(HttpSession session, Model model){
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		List<Category> list = categoryService.selectCategoryName();
		model.addAttribute("list", list);
		return "getCategoryList";
	}
	@GetMapping("/deleteCategory")
	public String deleteCategory(HttpSession session, Category category) {
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		categoryService.deleteCategory(category);
		
		return "redirect:/getCategoryList";
	}
	//카테고리 수정
	@GetMapping("/modifyCategory")
	public String modifyCategory(HttpSession session, Model model, @RequestParam(value="categoryNo", required = false) int categoryNo) {
		System.out.println(categoryNo + "///////");
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		
		Category category = categoryService.selectCategoryOne(categoryNo);
		model.addAttribute("category", category);
		return "modifyCategory";
	}
	@PostMapping("/modifyCategory")
	public String modifyCategory(HttpSession session, Category category) {
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		categoryService.modifyCategory(category);
		System.out.println(category + "??");
		return "redirect:/getCategoryList";
	}
}
