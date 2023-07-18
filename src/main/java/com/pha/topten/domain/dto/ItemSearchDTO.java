package com.pha.topten.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemSearchDTO {

	private int columnName;
	private String query;
	
	private int page=1;

}
