package com.pha.topten.domain.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.pha.topten.domain.entity.MyUserEntity;

import lombok.Setter;

@Setter
public class UsersaveDTO {

	private String email;
	private String pass;
	private String nickName;
	
	//DTO -> Entity
	public MyUserEntity toEntity(PasswordEncoder pe) {
		return MyUserEntity.builder()
						.email(email).nickName(nickName)
						.pass(pe.encode(pass))
						.build();
	}
	
	
}
