package com.pha.topten.domain.dto;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ItemUpdateDTO {

	private long no;
	private String title;
	private int price;
	private int stock;
	private String content;
}
