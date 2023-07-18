package com.pha.topten.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@SequenceGenerator(name = "gen_item_img", //제너레이터 이름
		sequenceName = "seq_item_img", initialValue = 1001, allocationSize = 1) //시퀀스 이름
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "itemImage")//my_user
@Entity
public class ItemImageEntity{
	
	@Id
	@GeneratedValue(generator = "gen_item_img", strategy = GenerationType.SEQUENCE)
	private long no;
	@Column(nullable = false)
	private String url; //s3경로
	@Column(nullable = false)
	private String orgName; //s3경로
	@Column(nullable = false)
	private String newName; //s3경로
	private boolean isList ; //false:content-img
	private boolean isDef ; //true:def-img
	
	private String bucketKey; //파일명
	
	
	//N:1 관계
	@ManyToOne
	private ItemEntity item;
}