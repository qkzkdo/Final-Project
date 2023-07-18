package com.pha.topten.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageEntityRepository extends JpaRepository<ItemImageEntity, Long>{

	Optional<ItemImageEntity> findByItemAndIsListAndIsDef(ItemEntity item, boolean isList, boolean isDef);


}
