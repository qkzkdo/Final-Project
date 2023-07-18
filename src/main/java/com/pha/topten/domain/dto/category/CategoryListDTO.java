package com.pha.topten.domain.dto.category;

import com.pha.topten.domain.entity.CategoryEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class CategoryListDTO {

	private long no;//카테고리 번호
	private CategoryEntity parentNo;
	private String kName;//카테고리 이름
	private String eName;//카테고리 이름
	
	public CategoryListDTO(CategoryEntity entity){
		no=entity.getNo();
		parentNo=entity.getParent();
		kName=entity.getKName();
		eName=entity.getEName();
	}
}