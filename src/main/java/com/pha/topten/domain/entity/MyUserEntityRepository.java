package com.pha.topten.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserEntityRepository extends JpaRepository<MyUserEntity, Long>{

	Optional<MyUserEntity> findByEmailAndIsSocial(String email, boolean isSocial);

	//회원가입 이메일 체크
	boolean existsByEmail(String email);

}
