package com.pha.topten.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.pha.topten.config.mapper.UserMapper;
import com.pha.topten.domain.dto.ItemSearchDTO;
import com.pha.topten.domain.dto.UsersaveDTO;
import com.pha.topten.domain.dto.cart.CartAddItemDTO;
import com.pha.topten.domain.dto.cart.CartListDTO;
import com.pha.topten.domain.entity.CartEntity;
import com.pha.topten.domain.entity.MyUserEntityRepository;
import com.pha.topten.security.MyRole;
import com.pha.topten.service.UserService;
import com.pha.topten.utils.PageData;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceProcess implements UserService {
	
	private final MyUserEntityRepository dao;
	private final PasswordEncoder pe;
	
	private final UserMapper mapper;

	//회원가입
	@Override
	public void saveProcess(UsersaveDTO dto) {
		dao.save(dto.toEntity(pe).addRole(MyRole.USER));
	}

	//회원가입 아이디 체크
	@Override
	public boolean emailCheckProcess(String email) {
		return dao.existsByEmail(email);
	}

	//장바구니 추가 비동기
	@Override
	public void cartAdd2(CartAddItemDTO dto, String email) {
		log.info("서비스 메서드 실행");
		long itemNo = dto.getItemNo().getNo(); //아이템번호
		mapper.cartAddItem2(dto, itemNo, email); 
		
		
	}
	
	//장바구니페이지 - 상품 가져오기
	@Override
	public void cartList(Model model, Authentication authentication) {
		String email = authentication.getName();
		log.info("유저이메일: "+email);
		List<CartListDTO> result = mapper.cartList(email);
		log.info("상품: "+result.toString());
		model.addAttribute("cartList", result);
		
	}

	//장바구니 페이지 - 상품 삭제
	@Override
	public void cartDelete(long no) {
		log.info("삭제할 상품 번호: "+no);
		mapper.cartDelete(no);
	}

}
