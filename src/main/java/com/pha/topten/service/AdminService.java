package com.pha.topten.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.pha.topten.domain.dto.ItemDetailDTO;
import com.pha.topten.domain.dto.ItemImgSaveDTO;
import com.pha.topten.domain.dto.ItemListDTO;
import com.pha.topten.domain.dto.ItemSaveDTO;
import com.pha.topten.domain.dto.ItemUpdateDTO;

public interface AdminService {

	/*     등록     */
	
	//상품등록+이미지 여러개 + 카테고리
	void saveProcess(ItemSaveDTO itemDto, ItemImgSaveDTO imgDto, Long long1);

	Map<String, String> tempUpload(MultipartFile tempImg);

	
	/*     조회     */
	
	//상품목록 조회 + 썸네일 + 페이징 *Mybatis
	void findAllProcessPage2(Model model, int page);
	
	
	/*     상세보기 영역     */
	
	//상품 상세보기 조회
	void detailProcess(long no, Model model);
	
	
	/*     삭제     */

	//상품목록에서 1개 삭제
	void deleteIdProcess(long no); //*Mybatis

	
	/*     수정     */
	//수정 페이지 이동
	void detailEditProcess(long no, Model model);

	//상품 디테일에서 수정하기
	void ItemEditProcess(long no, ItemUpdateDTO itemDto, ItemImgSaveDTO imgDto);

}
