package com.tiger.CharacterPalace.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tiger.CharacterPalace.entity.CharacterEntity;
import com.tiger.CharacterPalace.entity.HanziEntity;
import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.KdTree;
import com.tiger.CharacterPalace.model.Path;
import com.tiger.CharacterPalace.service.character.HanziEntityMapper;

public class JsonMapper {
	private static Gson gson;
	
	// i dont know why i wrote this when theres gson
	public static String curvesToJson(List<Path>curves) {	
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for(int i0=0; i0<curves.size(); i0++) {
			Path curve = curves.get(i0);
			if(i0>0) 
				stringBuilder.append(",");
			
			stringBuilder.append("{" + "\"type\":"+"\""+curve.getType()+"\"");
			stringBuilder.append(",\"coords\":[");
			for(int i=0; i<curve.getCoordinates().size(); i++) {
				double x = curve.getCoordinates().get(i)[0];
				double y = curve.getCoordinates().get(i)[1];
				if(i>0)
					stringBuilder.append(",");
				stringBuilder.append("{");
				stringBuilder.append("\"x\":"+x+","+"\"y\":"+y+"");
				stringBuilder.append("}");
			}
			stringBuilder.append("]}");
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
	
	public static String hanziEntitiesToJson(List<HanziEntity>entities) {
		gsonInit();
		List<Hanzi>hanzies = new ArrayList();
		for(HanziEntity e : entities) {
			hanzies.add(HanziEntityMapper.entityToHanzi(e));
		}
		
		return gson.toJson(hanzies);
	}
	
	public static Hanzi[] jsonToHanzi(String json){
		gsonInit();
		System.out.println("Gson action");
		Hanzi[] result= gson.fromJson(json, Hanzi[].class);
		return result;
	}
	
	public static String entityToJson(CharacterEntity entity) {
		gsonInit();
		return gson.toJson(entity);
	}
	
	public static String identityPointsToJson(List<double[]>points) {
		gsonInit();
		return gson.toJson(points);
	}
	
	public static String objectToJson(Object o) {
		gsonInit();
		return gson.toJson(o);
	}
	public static List<List<double[]>>jsonToIdentityPoints(String json){
		gsonInit();
		Type listType = new TypeToken<List<List<double[]>>>() {}.getType();
		List<List<double[]>> listOfLists = gson.fromJson(json, listType);
		return listOfLists;
	}	
	
	public static KdTree JsonToKdTree(String json) {
		gsonInit();
		KdTree.Node root = gson.fromJson(json, KdTree.Node.class);
		KdTree kdTree = new KdTree(root);
		return kdTree;
	}
	
	public static double[] jsonToDoubleArray(String json) {
		gsonInit();
		double[] array = gson.fromJson(json, double[].class);
		return array;
	}
	
	private static void gsonInit() {
		if(gson==null) {
			gson = new Gson();
		}
	}
}
