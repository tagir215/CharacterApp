package com.tiger.CharacterPalace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiger.CharacterPalace.entity.CharacterEntity;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity,Long>{
	 @Query("SELECT ce FROM CharacterEntity ce WHERE ce.unicode = :unicode")
	 CharacterEntity findByUnicode(@Param("unicode") int unicode);
	 
}
