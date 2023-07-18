package com.pha.topten.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
@Entity
public class CartEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long no;
	
	@ManyToOne //컬럼이름변경
	private ItemEntity item; //아이템번호
	
	@Column(nullable = false)
	private int amount; //수량
	
	@ManyToOne
	private MyUserEntity user; //유저번호 조인?
}