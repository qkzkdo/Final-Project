 package com.pha.topten.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pha.topten.domain.entity.MyUserEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

	//사용자 DB에서 user존재하면 UserDetails 정보를 넘겨주면 된다
	private final MyUserEntityRepository dao;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return new MyUserDetails(dao.findByEmailAndIsSocial(email, false)
							.orElseThrow(()->new UsernameNotFoundException("Bad User")));
	}

}
