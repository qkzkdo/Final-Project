package com.pha.topten.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pha.topten.domain.dto.ItemDetailDTO;
import com.pha.topten.domain.dto.ItemImgSaveDTO;
import com.pha.topten.domain.dto.ItemListDTO;
import com.pha.topten.domain.dto.ItemSaveDTO;
import com.pha.topten.domain.dto.ItemUpdateDTO;
import com.pha.topten.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminController {

	private final AdminService service;
	
	
	
	/*     관리자페이지 - 페이지 이동    */
	
	@ResponseBody
	@GetMapping("/test")
	public ResponseEntity<String> promiseTest() throws Exception{
		return ResponseEntity.ok().body("success");
	}
	
	//관리자페이지 이동
	@GetMapping("/admin")
	public String adminIndex() {
		return "admin/default";
	}
	
	//상품등록페이지 이동
	@GetMapping("/admin/item/new")
	public String adminItemNew() {
		return "admin/item/write";
	}
	
	//상품수정페이지 이동
	@GetMapping("/admin/edit/{no}")
	public String adminItemEditPage(@PathVariable long no, Model model) {
		service.detailEditProcess(no, model);
		return "admin/item/edit";
	}
	
	
	
	/*     관리자페이지 상품등록 영역     */
	
	//상품등록+이미지 여러개 + 카테고리 *JPA
	@PostMapping(value =  "/admin/item")
	public String adminItem(Long[] categoryNo,ItemSaveDTO itemDto,ItemImgSaveDTO imgDto) {
		
		service.saveProcess(itemDto, imgDto, Arrays.asList(categoryNo).stream().max(Long::compareTo).get());
		return "redirect:/admin/item/list";
	}
	
	//파일업로드
	//비동기구현 
	@PostMapping("/admin/item/temp")
	@ResponseBody
	public ResponseEntity<Map<String, String>> tempUpload(MultipartFile tempImg){ 
		return ResponseEntity.ok()
				.body(service.tempUpload(tempImg));//success: function(result){/*result로 path 전달*/}
				//바디에 결과를 문자열로 리턴하겠다
	}
	
	
	
	/*     관리자페이지 상품목록 조회 영역     */

	//상품목록 조회 + 썸네일 + 페이징
	@GetMapping("/admin/item/list")
	public String cateListPage(Model model, @RequestParam(defaultValue = "1") int page) {
		service.findAllProcessPage2(model, page); //*Mybatis
		return "admin/item/itemlist";
	}
	
	
	
	/*     관리자페이지 상품목록 삭제 영역     */

	//상품목록에서 1개 삭제(버튼)
	@DeleteMapping("/admin/delete/{no}")
	public String adminItemDeleteNo(@PathVariable long no) {
		service.deleteIdProcess(no);
		return "redirect:/admin/item/list";
	}
	
	
	
	/*     관리자페이지 상품목록 수정 영역     */
	
	//상품 수정
	@PutMapping("/admin/edit/{no}")
	public String adminItemEdit(@PathVariable long no, ItemUpdateDTO itemDto, ItemImgSaveDTO imgDto) {
		log.info("상품수정 컨트롤러 시작");
		service.ItemEditProcess(no, itemDto, imgDto); //상품 디테일에서 수정하기
		return "redirect:/admin/item/"+no;
	}
	
	
	
	/*     관리자페이지 상품목록 상세보기 영역     */
	
	//상품 상세보기 조회
	@GetMapping("/admin/item/{no}")
	public String adminItemDetail(@PathVariable long no, Model model) {
		service.detailProcess(no, model);
		return "admin/item/itemdetail";
	}
	
}