package com.pha.topten.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pha.topten.domain.dto.ItemSearchDTO;
import com.pha.topten.domain.dto.UsersaveDTO;
import com.pha.topten.domain.dto.cart.CartAddItemDTO;
import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.MyUserEntity;
import com.pha.topten.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService service;
	
	//회원가입
	@PostMapping("/signup")
	public String signup(UsersaveDTO dto) {
		service.saveProcess(dto);
		return "redirect:/";
	}
	
	//회원가입 아이디 체크
	@ResponseBody
	@GetMapping("/common/email-check")
	public boolean emailCheck(String email) {
		
		return service.emailCheckProcess(email);
	}
	
	
	/* 비동기 검색 영역 */
	
	@ResponseBody
	@PatchMapping("/rest-item/search")
	public ModelAndView restItemSearch() {
		ModelAndView mv = new ModelAndView("category/rest-item");
		return mv;
	}
	
	
	/* 장바구니 & 결제 영역 */
	
	//장바구니 추가 비동기 *디비에 저장
	@ResponseBody
	@PostMapping("/carts/add")
	public void cartAddItem2(CartAddItemDTO dto , Authentication authentication , HttpSession session) { //@ModelAttribute
		String email = authentication.getName();
		log.info("수량: "+dto.getAmount());
		log.info("유저이메일: "+email);
		log.info("아이템번호: "+dto.getItemNo().getNo());
		service.cartAdd2(dto, email);
		/* return "카트 담기 성공"; */
	}
	
	//장바구니페이지 - 상품 가져오기 *디비에서 불러오기
	//@ResponseBody
	@GetMapping("/carts/list")
	public String cartList(Model model, Authentication authentication) {
		service.cartList(model, authentication);
		
		return "user/cartpage";
	}

	//장바구니페이지 - 상품 삭제 * 디비에서 삭제
	@DeleteMapping("/carts/delete/{no}")
	public String cartDelete(@PathVariable long no) {
		log.info("컨트롤러실행");
		service.cartDelete(no);
		return "redirect:/carts/list";
	}

}
