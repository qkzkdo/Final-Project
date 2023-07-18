package com.pha.topten.domain.entity;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Long>{

	List<CategoryEntity> findAllByParent(CategoryEntity parent);
	
	List<CategoryEntity> findAllByParent_no(long no);

	//테스트코드 1차카테고리찾기
	CategoryEntity findBykName(String string);

}
