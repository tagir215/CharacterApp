package com.tiger.CharacterPalace.model;

public class Hanzi {
    private int frequency_rank;
    private String charcter;
    private String pinyin;
    private String definition;
    private String radical;
    private String color;
    private double radical_code;
    private String stroke_count;
    private int hsk_levl;
    private transient int general_standard_num;

    public Hanzi() {}

    public int getFrequency_rank() {
        return frequency_rank;
    }

    public void setFrequency_rank(int frequency_rank) {
        this.frequency_rank = frequency_rank;
    }

    public String getCharcter() {
        return charcter;
    }

    public void setCharcter(String charcter) {
        this.charcter = charcter;
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
    
    public int getGeneral_standard_num() {
        return general_standard_num;
    }

    public void setGeneral_standard_num(int general_standard_num) {
        this.general_standard_num = general_standard_num;
    }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
 
    
}

	 

