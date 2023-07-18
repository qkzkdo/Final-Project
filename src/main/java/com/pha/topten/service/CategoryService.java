package com.pha.topten.service;

import java.util.List;

import org.springframework.ui.Model;

import com.pha.topten.domain.dto.category.CategoryListDTO;
import com.pha.topten.domain.entity.ItemEntity;

public interface CategoryService {

	//유저화면 상품 카테고리 목록 *JPA
	List<CategoryListDTO> getCategoryProcess();
	
	List<CategoryListDTO> getCategoryProcess(long no);
	
	//유저페이지 #1차 카테고리별 상품조회+이미지+페이징 *Mybatis사용 //성공
	void findAllProcess3(Model model, String cname, int page);

	//유저화면 상품 카테고리 목록 *Mabatis
	List<CategoryListDTO> getCategoryProcess2(String cname);

	//유저화면 상품 상세보기 조회
	void categoryDetailProcess(Model model, long no);

	//유저페이지 #2차 카테고리별 상품조회+이미지 *Mybatis사용
	void findAllSecondCateProcess(Model model, long no, int page);

	


}
