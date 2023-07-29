package com.tiger.CharacterPalace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiger.CharacterPalace.entity.HanziEntity;

@Repository
public interface HanziRepository extends JpaRepository<HanziEntity,Long>{
	 @Query("SELECT ce FROM HanziEntity ce WHERE ce.unicode = :unicode")
	 List<HanziEntity> findByUnicode(@Param("unicode") int unicode);
	 
	 @Query("SELECT ce FROM HanziEntity ce WHERE ce.unicode IN (:unicodes)")
	 List<HanziEntity> findByUnicodes(@Param("unicodes") List<Integer> unicodes);
}
