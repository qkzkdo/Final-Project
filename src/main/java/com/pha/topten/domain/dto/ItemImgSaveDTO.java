package com.pha.topten.domain.dto;

import com.pha.topten.domain.entity.ItemEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ItemImgSaveDTO {

	//////////////////////////
	private String[] bucketKey;
	private String[] orgName;
	private String[] newName;
	
}
