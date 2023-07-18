package com.pha.topten.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pha.topten.security.MyRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@SequenceGenerator(name = "gen_user", //제너레이터 이름
		sequenceName = "seq_user", initialValue = 1001, allocationSize = 1) //시퀀스 이름
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MyUser") //my_user
@Entity 
public class MyUserEntity extends BaseDateEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_user") //시퀀스
	private long no;
	@Column(nullable = false, unique = true)
	private String email;
	private String pass;
	private String nickName;
	private boolean isSocial; //소셜로그인 여부
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "role")
	@ElementCollection(fetch = FetchType.EAGER) //1:N
	private Set<MyRole> roleSet = new HashSet<>();
	
	public MyUserEntity addRole(MyRole role) {
		roleSet.add(role);
		return this;
	}
	
}