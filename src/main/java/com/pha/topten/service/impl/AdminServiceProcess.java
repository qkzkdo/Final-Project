package com.pha.topten.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.pha.topten.config.mapper.AdminMapper;
import com.pha.topten.domain.dto.ItemDetailDTO;
import com.pha.topten.domain.dto.ItemDetailImageListDTO;
import com.pha.topten.domain.dto.ItemImgSaveDTO;
import com.pha.topten.domain.dto.ItemListDTO;
import com.pha.topten.domain.dto.ItemListDTO2;
import com.pha.topten.domain.dto.ItemSaveDTO;
import com.pha.topten.domain.dto.ItemUpdateDTO;
import com.pha.topten.domain.entity.CategoryEntity;
import com.pha.topten.domain.entity.CategoryEntityRepository;
import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.ItemEntityRepository;
import com.pha.topten.domain.entity.ItemImageEntity;
import com.pha.topten.domain.entity.ItemImageEntityRepository;
import com.pha.topten.service.AdminService;
import com.pha.topten.utils.FileUploadUtil;
import com.pha.topten.utils.PageData;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AdminServiceProcess implements AdminService{
	
	private final AmazonS3Client s3Client;
	private final ItemImageEntityRepository imageRepo;
	private final ItemEntityRepository itemRepo;
	private final CategoryEntityRepository cateRepo;
	
	private final AdminMapper mapper;
	
	//게시글 저장 전 이미지 임시 저장 공간
	@Value("${cloud.aws.s3.bucket.temp}")
	private String TEMP_PATH;
	//게시글 저장시 이미지 업로드 공간
	@Value("${cloud.aws.s3.bucket.upload}")
	private String UPLOAD_PATH;
	//aws-s3 버킷 이름
	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;
	
	
	
	/*     상품등록 영역     */
	
	//temp 업로드
	@Override
	public Map<String, String> tempUpload(MultipartFile tempImg) { //문자열로 경로를 받겠다
		return FileUploadUtil.s3Upload(s3Client, BUCKET, TEMP_PATH , tempImg);
	}

	//상품등록+이미지 여러개 저장
	@Override
	public void saveProcess(ItemSaveDTO itemDto, ItemImgSaveDTO imgDto,  Long categoryId) {
		
		ItemEntity item= itemRepo.save(itemDto.toItemEntity()
				.addCategory(cateRepo.findById(categoryId).orElseThrow()));
		
		String[] bucketKeies=imgDto.getBucketKey();
		String[] orgNames=imgDto.getOrgName();
		String[] newNames=imgDto.getNewName();
		for(int i=0; i<bucketKeies.length; i++) {
			if(bucketKeies[i]==null || bucketKeies[i]=="")continue;
			String newName=newNames[i];
			String orgName=orgNames[i];
			String uploadKey=UPLOAD_PATH+newName;
			String url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, bucketKeies[i], uploadKey);
			if(i==0) {

				imageRepo.save(ItemImageEntity.builder()
						.bucketKey(bucketKeies[i])
						.isDef(true).isList(true)
						.url(url).orgName(orgName).newName(newName)
						.item(item)
						.build());
			}else {
			
				imageRepo.save(ItemImageEntity.builder()
						.bucketKey(bucketKeies[i])
						.isList(true)
						.url(url).orgName(orgName).newName(newName)
						.item(item)
						.build());
			}//if~else
		}//for
	}

	/*     관리자페이지 수정 기능     */

	//수정 페이지 이동
	@Override
	public void detailEditProcess(long no, Model model) {
		model.addAttribute("detailedit",mapper.itemDetail(no));
	}
	
	//상품 디테일에서 수정하기
	@Override
	public void ItemEditProcess(long no, ItemUpdateDTO itemDto, ItemImgSaveDTO imgDto) {
		log.info("상품수정 서비스임플 시작");
		
		//item수정
		ItemEntity item = itemRepo.findById(no).orElseThrow(() -> new RuntimeException("Item not found"));
		log.info("게시글 번호"+item.getNo());

		
		//*
		item.setTitle(itemDto.getTitle());
		log.info(item.getTitle());
		item.setPrice(itemDto.getPrice());
		log.info(item.getPrice());
		item.setContent(itemDto.getContent());
		log.info(item.getContent());
		item.setStock(itemDto.getStock());
		log.info(item.getStock());
		//*/
		itemRepo.save(item);
		
		
		//이미지 삭제
		mapper.itemImageDelete(no);
		
		//이미지 업로드
		//*
		String[] bucketKeies=imgDto.getBucketKey();
		log.info("bucketKeies: "+imgDto.getBucketKey());
		String[] orgNames=imgDto.getOrgName();
		String[] newNames=imgDto.getNewName();
		for(int i=0; i<bucketKeies.length; i++) {
			if(bucketKeies[i]==null || bucketKeies[i]=="")continue;
			String newName=newNames[i];
			log.info("newName: "+newName.toString());
			String orgName=orgNames[i];
			log.info("orgName: "+orgName.toString());
			String uploadKey=UPLOAD_PATH+newName;
			log.info("uploadKey: "+uploadKey.toString());
			String url=FileUploadUtil.s3TempToSrc(s3Client, BUCKET, bucketKeies[i], uploadKey);
			log.info("url: "+url.toString());
			if(i==0) {
				log.info("item1: "+item.getTitle());
				imageRepo.save(ItemImageEntity.builder()
						.bucketKey(bucketKeies[i])
						.isDef(true).isList(true)
						.url(url).orgName(orgName).newName(newName)
						.item(item)
						.build());
				log.info("item1 수정 후: "+item.getTitle());
			}else {
				log.info("item2: "+item.getTitle());
				imageRepo.save(ItemImageEntity.builder()
						.bucketKey(bucketKeies[i])
						.isList(true)
						.url(url).orgName(orgName).newName(newName)
						.item(item)
						.build());
				log.info("item2 수정 후: "+item.getTitle());
			}//if~else
		}//for
		//*/
	}
	
	
	/*     상품목록 조회 영역     */
	
	//상품목록 조회 + 썸네일 + 페이징 *Mybatis
	@Override
	public void findAllProcessPage2(Model model, int page) {
		if(page<1)page=1;
		int limit=10; //한 화면에 보이는 개수
		int offset=(page-1)*limit; //0:1p, 5:2p
		
		List<ItemListDTO2> result=mapper.findCateItemImgPage(limit, offset);
		model.addAttribute("list", result);
		
		int rowCount=mapper.countAll();
		
		
		model.addAttribute("pd", PageData.create(page, limit, rowCount, 6));
		
	}
	
	
	/*     관리자페이지 삭제 기능     */
	
	//상품목록에서 1개 삭제
	@Override
	public void deleteIdProcess(long no) {
		mapper.itemDeleteId(no); //*mybatis
		
	}
	
	
	
	/*     관리자페이지 상세보기 기능     */
	
	//상품목록 상세보기
	@Override
	public void detailProcess(long no, Model model) {
		ItemDetailDTO result = mapper.itemDetail(no);
		List<ItemDetailImageListDTO> resultimages = mapper.itemImageList(no);
		model.addAttribute("itemdetail", result);
		model.addAttribute("itemimagelist",resultimages);
	}
	
}