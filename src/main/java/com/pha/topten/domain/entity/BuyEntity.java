package com.pha.topten.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buy")
@Entity
public class BuyEntity {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long no; //주문번호
	@Column(nullable = false)
	private String resName; //받는사람 이름
	@Column(nullable = false)
	private String resAddress; //받는사람 주소
	@Column(nullable = false)
	private String resPhone; //받는사람 번호
	
}