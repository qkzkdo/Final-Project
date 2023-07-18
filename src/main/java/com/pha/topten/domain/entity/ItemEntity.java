package com.pha.topten.domain.entity;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@SequenceGenerator(name = "gen_item", //제너레이터이름
		sequenceName = "seq_item", initialValue = 1001, allocationSize = 1) //시퀀스이름
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "item")
@Entity
public class ItemEntity extends BaseDateEntity{
	
	@Id
	@GeneratedValue(generator = "gen_item", strategy = GenerationType.SEQUENCE)
	private long no;
	@Column(nullable = false)
	private String title; //상품명
	@Column(nullable = false)
	private int price; //가격
	@Column(nullable = false)
	private int stock; //재고
	@Column(nullable = false)
	private String content; //상세내용

	
	//*
	@Builder.Default 
	@JoinTable(name = "item_category"
			, joinColumns = @JoinColumn(name="item_no")
			, inverseJoinColumns = @JoinColumn(name="category_no"))
	@ManyToMany 
	private List<CategoryEntity> categoryList = new Vector<>();
	
	//편의메서드, set카테고리 처럼 보여지는
	public ItemEntity addCategory(CategoryEntity category) {
		categoryList.add(category);
		return this;
	}
	
}
