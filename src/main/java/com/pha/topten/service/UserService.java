package com.pha.topten.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.pha.topten.domain.dto.ItemSearchDTO;
import com.pha.topten.domain.dto.UsersaveDTO;
import com.pha.topten.domain.dto.cart.CartAddItemDTO;

public interface UserService {

	//회원가입
	void saveProcess(UsersaveDTO dto);

	//장바구니 추기 비동기
	void cartAdd2(CartAddItemDTO dto, String email);

	//장바구니페이지 - 상품 가져오기
	void cartList(Model model, Authentication authentication);

	//장바구니페이지 - 상품 삭제
	void cartDelete(long no);

	//회원가입 아이디 체크
	boolean emailCheckProcess(String email);

}
