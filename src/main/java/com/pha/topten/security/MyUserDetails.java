package com.pha.topten.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pha.topten.domain.entity.MyUserEntity;

import lombok.Getter;

@Getter
public class MyUserDetails extends User{

	private String nick;
	private String email;
	
	public MyUserDetails(MyUserEntity entity) {
		this(entity.getEmail(), entity.getPass(), entity.getRoleSet().stream()
				.map(role->new SimpleGrantedAuthority(role.roleName()))
				.collect(Collectors.toSet()) );
		this.nick = entity.getNickName();
		this.email = entity.getEmail();
	}

	private MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	
}
