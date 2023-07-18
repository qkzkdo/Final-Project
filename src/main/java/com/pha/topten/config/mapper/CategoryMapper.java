package com.pha.topten.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pha.topten.domain.dto.ItemDetailDTO;
import com.pha.topten.domain.dto.ItemDetailImageListDTO;
import com.pha.topten.domain.dto.ItemListDTO;
import com.pha.topten.domain.dto.ItemListDTO2;
import com.pha.topten.domain.dto.category.CategoryListDTO;
import com.pha.topten.domain.entity.ItemEntity;

@Mapper
public interface CategoryMapper {

	//유저페이지 #1차 카테고리별 상품조회+이미지+페이징 *Mybatis사용 //성공
	List<ItemListDTO2> findCateItemImgPage(@Param("limit") int limit, @Param("offset") int offset, @Param("cname") String cname);

	//전체게시글조회
	@Select("select count(*) from item")
	int countAll();

	//유저화면 상품 카테고리 목록
	List<CategoryListDTO> getCategoryProcess();
	
	//2차카테고리 조회 
	List<CategoryListDTO> getCategoryProcess(String cname);

	//카테고리별 게시글 개수 조회
	int countCategory(String cname);
	
	//카테고리 상품 상세보기 조회
	ItemDetailDTO categoryDetail(long no);

	//카테고리 상품 상세보기 이미지 리스트 조회
	List<ItemDetailImageListDTO> categoryDetailList(long no);

	//카테고리별 게시글 개수 조회(하위 모든 카테고리)
	int countCategoryAll(String cname);

	//유저페이지 #2차 카테고리별 상품조회+이미지 *Mybatis사용
	List<ItemListDTO2> findCateItemImgPageSecond(@Param("limit") int limit, @Param("offset") int offset, @Param("no") long no);

	//2차 카테고리(하위)별 게시글 개수 조회
	int countCategoryAllSecond(long no);








}
