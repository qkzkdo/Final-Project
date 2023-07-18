package com.pha.topten.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item_io")
@Entity
public class ItemIoEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long no; //입출고 번호
	@Column(nullable = false)
	private int amount; //입출고량
	@Column(nullable = false)
	private boolean status; //상태
	@Column(nullable = false)
	private LocalDateTime pDate; //입출고 날짜
	
	@ManyToOne
	private MyUserEntity userNo;
	
	@ManyToOne
	private ItemEntity itemNo;
	
	@ManyToOne
	private BuyEntity buyNo;
	
}
