package com.tiger.CharacterPalace.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="HanziTable")
public class HanziEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	@Column
	private int frequency_rank;
	@Column
    private int unicode;
    @Column
    private String pinyin;
    @Column
    private String radical;
    @Column
    private double radical_code;
    @Column
    private String stroke_count;
    @Column
    private int hsk_levl;
    @Column
    private String color;
    
    @OneToMany(mappedBy = "hanziEntity", cascade = CascadeType.ALL)
    private List<DefinitionEntity> definitions;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public int getFrequency_rank() {
		return frequency_rank;
	}

	public void setFrequency_rank(int frequency_rank) {
		this.frequency_rank = frequency_rank;
	}

	public int getUnicode() {
		return unicode;
	}

	public void setUnicode(int unicode) {
		this.unicode = unicode;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getRadical() {
		return radical;
	}

	public void setRadical(String radical) {
		this.radical = radical;
	}

	public double getRadical_code() {
		return radical_code;
	}

	public void setRadical_code(double radical_code) {
		this.radical_code = radical_code;
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

	public List<DefinitionEntity> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<DefinitionEntity> definitions) {
		this.definitions = definitions;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
    
    
    
}
