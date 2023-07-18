package com.pha.topten.domain.dto;

import com.pha.topten.domain.entity.ItemEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSaveDTO {

	private long no;
	private String title;
	private int price;
	private int stock;
	private String content;
	
	public ItemEntity toItemEntity() {
		return ItemEntity.builder()
				.title(title).price(price).stock(stock).content(content)
				.build();
	}
	
	//////////////////////////
	private String bucketKey;
	private String orgName;
	private String newName;
	
}
