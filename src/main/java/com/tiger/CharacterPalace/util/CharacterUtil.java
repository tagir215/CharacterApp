package com.tiger.CharacterPalace.util;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;

import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.Service.KdTree;

public class CharacterUtil {
	private static HashMap<Integer,CharacterEntity>entityMap;
	private static HashMap<Integer,KdTree>kdTreeMap;
	private static Font font;
	
	
	public static void setEntityMap(List<CharacterEntity>entities) {
		entityMap = new HashMap<>();
		for(CharacterEntity e : entities) {
			entityMap.put(e.getUnicode(), e);
		}
	}
	public static void setKdTreeMap(List<CharacterEntity>entities) {
		kdTreeMap = new HashMap<>();
		for(CharacterEntity e : entities) {
			KdTree kdTree = JsonMapper.JsonToKdTree(e.getIdentityData());
			kdTreeMap.put(e.getUnicode(),kdTree);
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
	
}
