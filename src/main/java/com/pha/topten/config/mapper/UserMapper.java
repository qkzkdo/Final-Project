package com.pha.topten.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.pha.topten.domain.dto.ItemSearchDTO;
import com.pha.topten.domain.dto.cart.CartAddItemDTO;
import com.pha.topten.domain.dto.cart.CartListDTO;
import com.pha.topten.domain.entity.CartEntity;
import com.pha.topten.domain.entity.ItemEntity;

@Mapper
public interface UserMapper {

	//장바구니 추가 비동기
	void cartAddItem2(@Param("dto") CartAddItemDTO dto, @Param("itemNo") long itemNo, @Param("email") String email);

	//장바구니페이지 - 상품 가져오기
	List<CartListDTO> cartList(String email);

	//장바구니페이지 - 상품 삭제
	void cartDelete(long no);

}
