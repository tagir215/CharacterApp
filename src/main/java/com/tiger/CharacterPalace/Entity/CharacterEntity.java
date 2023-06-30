package com.tiger.CharacterPalace.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ChinaChars")
public class CharacterEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private int unicode;
	
	@Column(nullable=false, columnDefinition = "TEXT")
	private String graphicData;
	
	@Column(nullable=false, columnDefinition= "TEXT")
	private String identityData;
	
	@Column(nullable=true)
	private int frequency_rank;
	
	@Column(nullable=true)
	private String pinyin;
	
	@Column(nullable=true)
	private String definition;
	
	@Column(nullable=true)
	private String stroke_count;
	
	@Column(nullable=true)
	private int hsk_levl;
	
	
	@Column(nullable=true)
	private String color;
	

	public CharacterEntity() {};

	public long getId() {
		return id;
	}


	public int getUnicode() {
		return unicode;
	}

	public void setUnicode(int unicode) {
		this.unicode = unicode;
	}

	public String getGraphicData() {
		return graphicData;
	}

	public void setGraphicData(String graphicData) {
		this.graphicData = graphicData;
	}
	
	public String getIdentityData() {
		return identityData;
	}

	public void setIdentityData(String identityData) {
		this.identityData = identityData;
	}

	public int getFrequency_rank() {
		return frequency_rank;
	}

	public void setFrequency_rank(int frequency_rank) {
		this.frequency_rank = frequency_rank;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getStroke_count() {
		return stroke_count;
	}

	public void setStroke_count(String stroke_count) {
		this.stroke_count = stroke_count;
	}

	public int getHsk_levl() {
		return hsk_levl;
	}

	public void setHsk_levl(int hsk_levl) {
		this.hsk_levl = hsk_levl;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
