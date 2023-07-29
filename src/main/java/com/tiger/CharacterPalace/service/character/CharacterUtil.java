package com.tiger.CharacterPalace.service.character;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;

import com.tiger.CharacterPalace.entity.CharacterEntity;
import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.KdTree;
import com.tiger.CharacterPalace.model.LmTrie;
import com.tiger.CharacterPalace.util.JsonMapper;

public class CharacterUtil {
	private static HashMap<Integer,CharacterEntity>entityMap = new HashMap<>();
	private static HashMap<Integer,KdTree>kdTreeMap = new HashMap<>();
	private static HashMap<Integer,List<List<double[]>>>identityMap = new HashMap<>();
	private static Font font;
	private static LmTrie lmTrie;
	private static Hanzi[] hanzies;
	
	
	
	public static Hanzi[] getHanzies() {
		return hanzies;
	}
	public static void setHanzies(Hanzi[] hanzies) {
		CharacterUtil.hanzies = hanzies;
	}
	public static LmTrie getLmTrie() {
		return lmTrie;
	}
	public static void setLmTrie(LmTrie lmTrie) {
		CharacterUtil.lmTrie = lmTrie;
	}
	public static void setEntityMap(List<CharacterEntity>entities) {
		for(CharacterEntity e : entities) {
			entityMap.put(e.getUnicode(), e);
		}
	}
	public static void setIdentityMap(List<CharacterEntity>entities) {
		for(CharacterEntity entity : entities) {
			identityMap.put(entity.getUnicode(), JsonMapper.jsonToIdentityPoints(entity.getIdentityData()));
		}
	}
	public static void setKdTreeMap(List<CharacterEntity>entities) {
		for(CharacterEntity entity : entities) {
			kdTreeMap.put(entity.getUnicode(), JsonMapper.JsonToKdTree(entity.getIdentityData()));
		}
	}
	
	public static HashMap<Integer,CharacterEntity>getEntityMap(){
		return entityMap;
	}
	
	public static HashMap<Integer, KdTree> getKdTreeMap() {
		return kdTreeMap;
	}
	
	public static Font getFont() {
		return font;
	}
	public static void setFont(Font font) {
		CharacterUtil.font = font;
	}
	public static HashMap<Integer, List<List<double[]>>> getIdentityMap() {
		return identityMap;
	}
	
	
}
