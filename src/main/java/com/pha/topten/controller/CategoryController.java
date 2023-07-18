package com.pha.topten.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pha.topten.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class CategoryController {

	private final CategoryService service;
	
	
	
	/*     유저페이지 상품조회 영역     */

	//유저페이지 #1차 카테고리별 상품조회+이미지+페이징 *Mybatis사용 //성공
	@GetMapping("/category/{cname}")
	public String cateListPage(Model model, @PathVariable String cname, @RequestParam(defaultValue = "1") int page) {
		log.info("카테고리이름 : "+cname);
		service.findAllProcess3(model, cname, page);
		return "category/catelist3";
	}
	
	//유저페이지 #2차 카테고리별 상품조회+이미지 *Mybatis사용 //성공
	@GetMapping("/category/second/{no}")
	public String cateSecondListPage(Model model, @PathVariable long no, @RequestParam(defaultValue = "1") int page) {
		service.findAllSecondCateProcess(model, no, page);
		return "category/catelistsecond";
	}
	
	
	/*     유저페이지 상품 상세보기 영역     */
	@GetMapping("/category/detail/{no}")
	public String categoryDetail(Model model, @PathVariable long no) {
		service.categoryDetailProcess(model, no);
		return "category/catelistdetail";
	}
	
	
	
	/*     비동기 처리 영역     */
	
	//유저화면 상품 카테고리 목록
	@ResponseBody
	@GetMapping("/common/category/{cname}")
	public ModelAndView category(@PathVariable String cname) {
		log.info("category() 실행");
		log.info("매핑카테고리: "+cname);
		ModelAndView mv=new ModelAndView("category/cate-list");
		mv.addObject("catelist", service.getCategoryProcess2(cname)); //catelist로 변경 *Mybatis
		log.info("카테고리이름 불러오기"+mv.toString());
		return mv;
	}
	
	//관리자페이지 상품 등록 카테고리 선택
	@ResponseBody // ModelAndView : html을 을 리턴
	@GetMapping("/common/category-select")
	public ModelAndView categorySelect() {
		ModelAndView mv=new ModelAndView("category/list-select");
		mv.addObject("list", service.getCategoryProcess());
		return mv;
	}
	
	@ResponseBody // ModelAndView : html을 을 리턴
	@GetMapping("/common/category-select/{no}")
	public ModelAndView categorySelect(@PathVariable long no) {
		log.info("categorySelect() 실행");
		ModelAndView mv=new ModelAndView("category/list-select");
		mv.addObject("list", service.getCategoryProcess(no));
		return mv;
	}
	
	
}