package com.pha.topten.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ItemDetailDTO {

	private long no;
	private String title;
	private String content;
	private int price;
	private int stock;
	private LocalDateTime createdDate;
	
	//이미지정보
	private String url;
	private String bucketKey;
	private String orgName;
	private String newName;
	
	//카테고리 정보
	private String kName;

}
