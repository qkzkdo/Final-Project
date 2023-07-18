package com.pha.topten.domain.dto;

import java.time.LocalDateTime;

import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.ItemImageEntity;

import lombok.Getter;

@Getter
public class ItemListDTO2 {
	
	private long no;
	private String title;
	private String content;
	private String writer;
	private int price;
	private int stock;
	private LocalDateTime createdDate;
			
	/////////////////////
	//def이미지 정보들
	private String url;
	private String bucketKey;
	private String orgName;
	private String newName;
	
	
}