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
	
	//카테고리 추가 Form
	@GetMapping("/addCategory")
	public String insertCategory(HttpSession session) {
		//관리자가 로그인되어있지 않다면 index로 돌려보내는 조건문
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		//addCategory.html 호출
		return "addCategory";
	}
	
	//카테고리 추가 Action
	@PostMapping("/addCategory")
	public String insertCategory(HttpSession session, Category category) {
		//addCategory Form에서 넘겨받은 category 변수값을 categoryService 내 addCategory 메소드로 넘김
		categoryService.addCategory(category);
		//디버깅
			System.out.println(category + "/category/postAddCategory");
		//메소드가 정상적으로 작동완료하면 getCategoryList.html로 돌아간다.
		return "redirect:/getCategoryList";
	}
	
	//카테고리 리스트 Form
	@GetMapping("/getCategoryList")
	public String getCategoryList(Model model){
		//categoryService 내 selectCategoryName 메소드의 값을 list 변수에 리스트(배열)로 담는다.
		List<Category> list = categoryService.selectCategoryName();
		//디버깅
			System.out.println(list + "/list/getCategoryList");
		//list 변수값을 getCategoryList.html로 보낸다.
		model.addAttribute("list", list);
		return "getCategoryList";
	}
	
	//카테고리 삭제 Action
	@GetMapping("/deleteCategory")
	public String deleteCategory(HttpSession session, Category category) {
		//관리자로 로그인 되어있지 않다면 index로 돌려보내는 조건문 
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		//categoryService 내 deleteCategory 메소드로 category 변수 전달 	
		categoryService.deleteCategory(category);
		//메소드가 정상적으로 작동되면 getCategoryList로 돌아감
		return "redirect:/getCategoryList";
	}
	
	//카테고리 수정 Form
	@GetMapping("/modifyCategory")
	public String modifyCategory(HttpSession session, Model model, @RequestParam(value="categoryNo", required = false) int categoryNo) {
		//getCategoryList에서 받은 categoryNo값이 제대로 들어왔는지 디버깅
			System.out.println(categoryNo + "/categoryNo/getModifyCategory");
		//관리자로 로그인 되어있는게 아니면 index로 복귀
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		//categoryService 내 selectCategoryOne 메소드를 category변수에 기입
		Category category = categoryService.selectCategoryOne(categoryNo);
		//디버깅
			System.out.println(category + "/category/getModifyCategory");
		//modifyCategory.html로 .category변수값를 보냄 
		model.addAttribute("category", category);
		//modifyCategory.html 호출
		return "modifyCategory";
	}
	
	//카테고리 수정 Action
	@PostMapping("/modifyCategory")
	public String modifyCategory(HttpSession session, Category category) {
		//관리자로 로그인이 되어있지 않다면 index로 돌아가는 조건문
		if(session.getAttribute("loginAdmin") == null) {
			return "redirect:/index";
		}
		//카테고리 수정 Form에서 받은  category 변수값을 categoryService 내 modifyCategory 메소드로 보냄
		categoryService.modifyCategory(category);
		//디버깅
		System.out.println(category + "??");
		//메소드가 작동완료 하면 getCategoryList.html를 호출
		return "redirect:/getCategoryList";
	}
}
