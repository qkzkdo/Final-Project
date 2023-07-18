package com.pha.topten.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pha.topten.domain.dto.ItemDetailDTO;
import com.pha.topten.domain.dto.ItemDetailImageListDTO;
import com.pha.topten.domain.dto.ItemListDTO2;
import com.pha.topten.domain.dto.ItemSaveDTO;
import com.pha.topten.domain.dto.ItemUpdateDTO;
import com.pha.topten.domain.entity.ItemEntity;

@Mapper
public interface AdminMapper {

	//관리자페이지 상품목록에서 1개 삭제
	void itemDeleteId(long no);
	
	//관리자페이지 상품목록 조회 + 썸네일 + 페이징
	List<ItemListDTO2> findCateItemImgPage(@Param("limit") int limit, @Param("offset") int offset);

	//전제 아이템 개수 조회
	@Select("select count(*) from item")
	int countAll();

	//관리자페이지 상품 상세보기 조회
	ItemDetailDTO itemDetail(long no);

	//관리자페이지 상품 상세보기 이미지 리스트 조회
	List<ItemDetailImageListDTO> itemImageList(long no);

	
	/* 상품 수정 영역 */
	
	//수정 - item 테이블만 수정
	ItemEntity itemUpdate(@Param("no") long no, @Param("itemDto") ItemUpdateDTO itemDto);
	//수정 - item_image 테이블 삭제
	void itemImageDelete(long no);
	


}
