package com.pha.topten.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.amazonaws.services.s3.AmazonS3Client;
import com.pha.topten.config.mapper.CategoryMapper;
import com.pha.topten.domain.dto.ItemDetailDTO;
import com.pha.topten.domain.dto.ItemDetailImageListDTO;
import com.pha.topten.domain.dto.ItemListDTO;
import com.pha.topten.domain.dto.ItemListDTO2;
import com.pha.topten.domain.dto.category.CategoryListDTO;
import com.pha.topten.domain.entity.CategoryEntity;
import com.pha.topten.domain.entity.CategoryEntityRepository;
import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.ItemEntityRepository;
import com.pha.topten.domain.entity.ItemImageEntity;
import com.pha.topten.domain.entity.ItemImageEntityRepository;
import com.pha.topten.service.CategoryService;
import com.pha.topten.utils.PageData;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceProcess implements CategoryService {
	
	private static final CategoryEntity parentCategory = null;
	private final AmazonS3Client s3Client;
	private final ItemImageEntityRepository imageRepo;
	private final ItemEntityRepository itemRepo;
	private final CategoryEntityRepository repo;
	
	private final CategoryMapper mapper;
	
	@Value("${cloud.aws.s3.bucket.temp}")
	private String TEMP_PATH;
	@Value("${cloud.aws.s3.bucket.upload}")
	private String UPLOAD_PATH;
	
	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;
	
	
	
	/*     유저페이지 상픔목록 카테고리 조회     */
	
	//*
	//유저화면 상품 카테고리 목록 *JPA
	@Override
	public List<CategoryListDTO> getCategoryProcess() {
		return repo.findAllByParent(null).stream() //1차카테고리 찾기
				.map(CategoryListDTO::new)
				.collect(Collectors.toList());
		
	}
	//*/
	
	
	//유저화면 상품 카테고리 목록 *Mybatis
	//*
	@Override
	public List<CategoryListDTO> getCategoryProcess2(String cname) {
		log.info("getCategoryProcess2() 실행");
		List<CategoryListDTO> result = mapper.getCategoryProcess(cname);
		log.info("디비에서 불러온 2차카테고리 목록"+result.toString());
	    return result;
	}
	//*/
	
	//*
	@Override
	public List<CategoryListDTO> getCategoryProcess(long no) {
		
		return repo.findAllByParent(repo.findById(no).orElse(null)).stream()
								.map(CategoryListDTO::new)
								.collect(Collectors.toList());
								
	}
	//*/
	
	
	//유저페이지 #1차 카테고리별 상품조회+이미지+페이징 *Mybatis사용
	@Override
	public void findAllProcess3(Model model, String cname, int page) {
		//int page=2;
		if(page<1)page=1;
		int limit=8; //한 화면에 보이는 개수
		int offset=(page-1)*limit; //0:1p, 5:2p
		
		List<ItemListDTO2> result=mapper.findCateItemImgPage(limit, offset, cname);//하위 카테고리가 있는 아이템까지 모두 조회
		model.addAttribute("itemlist", result);
		
		int rowCount=mapper.countCategoryAll(cname); //하위차카테고리 모두 조회
		log.info(cname+" 카테고리의 item 개수: "+rowCount);
		
		model.addAttribute("pd", PageData.create(page, limit, rowCount, 6));
		
		//카테고리 이름 목록
		List<CategoryListDTO> catelist = mapper.getCategoryProcess(cname);
		model.addAttribute("catelist",catelist);
		
		//1차카테고리
		model.addAttribute("cate1",cname);
	}

	//유저페이지 #2차 카테고리별 상품조회+이미지 *Mybatis
	@Override
	public void findAllSecondCateProcess(Model model, long no, int page) {
		
		if(page<1)page=1;
		int limit=8; //한 화면에 보이는 개수
		int offset=(page-1)*limit; //0:1p, 5:2p		
		
		List<ItemListDTO2> result=mapper.findCateItemImgPageSecond(limit, offset, no);
		model.addAttribute("itemlist", result);
		log.info("2차카테고리별 상품조회: "+result.toString());
		
		//페이징
		int rowCount=mapper.countCategoryAllSecond(no); 
		model.addAttribute("pd", PageData.create(page, limit, rowCount, 6));
	}
	

	
	/*     유저페이지 상품 상세보기 조회     */
	
	@Override
	public void categoryDetailProcess(Model model, long no) {
		ItemDetailDTO result =  mapper.categoryDetail(no);
		List<ItemDetailImageListDTO> resultList = mapper.categoryDetailList(no);
		log.info("상품 이미지 리스트: "+resultList.toString());
		model.addAttribute("itemdetail",result);
		model.addAttribute("itemimagelist",resultList);
	}

}
