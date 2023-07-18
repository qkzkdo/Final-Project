package com.pha.topten.domain.dto.cart;

import org.springframework.security.core.Authentication;

import com.pha.topten.domain.entity.CartEntity;
import com.pha.topten.domain.entity.ItemEntity;
import com.pha.topten.domain.entity.MyUserEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class CartAddItemDTO {

	private long no;
	private MyUserEntity email;
	private ItemEntity itemNo;
	private int amount;
	
	public CartEntity toEntity(Authentication auth) {
		return CartEntity.builder()
				.user(email).item(itemNo).amount(amount)
				.build();
	}
	
	public void setUserNo(long no) {
		this.no = no;
	}

	public void setEmail(MyUserEntity email) {
		this.email = email;
	}
	
	
}
