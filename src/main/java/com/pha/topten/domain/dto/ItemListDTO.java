package com.pha.topten.domain.dto;

import java.time.LocalDateTime;

import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.ItemImageEntity;

import lombok.Getter;

@Getter
public class ItemListDTO {
	
	private long no;
	private String title;
	private int price;
	private int stock;
	private LocalDateTime createdDate;
			
	/////////////////////
	//def이미지 정보들
	private String url;
	private String bucketKey;
	private String orgName;
	private String newName;
	
	
	//def이미지만 추가해주는 편의메서드
	public ItemListDTO defImg(ItemImageEntity ie) {
		url=ie.getUrl();
		bucketKey=ie.getBucketKey();
		orgName=ie.getOrgName();
		newName=ie.getNewName();
		return this;
	}

	
	public ItemListDTO(ItemEntity item) {
		no=item.getNo();
		price=item.getPrice();
		title=item.getTitle();
		stock=item.getStock();
		createdDate=item.getCreatedDate();
	}
	
}