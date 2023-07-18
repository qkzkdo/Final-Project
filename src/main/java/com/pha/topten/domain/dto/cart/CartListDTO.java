package com.pha.topten.domain.dto.cart;

import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.MyUserEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CartListDTO {
	
	private long no;
	private long itemNo; //아이템번호
	private String title; //상품명
	private int price; //가격
	private int amount; //수량
	private long userNo; //유저번호 조인?
	private String email;
	private String nickName;	
	private String url;
}
