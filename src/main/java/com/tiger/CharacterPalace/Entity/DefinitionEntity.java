package com.tiger.CharacterPalace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="DefinitionTable")
public class DefinitionEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column 
	private String definition;
	
	@Column(columnDefinition="TEXT")
	private String embeddings;
	
	@ManyToOne
	private HanziEntity hanziEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getEmbeddings() {
		return embeddings;
	}

	public void setEmbeddings(String embeddings) {
		this.embeddings = embeddings;
	}

	public HanziEntity getHanziEntity() {
		return hanziEntity;
	}

	public void setHanziEntity(HanziEntity hanziEntity) {
		this.hanziEntity = hanziEntity;
	}
	
	
}
