package com.tiger.CharacterPalace.util;

import java.util.List;

import com.google.gson.Gson;
import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.Service.Hanzi;
import com.tiger.CharacterPalace.Service.KdTree;
import com.tiger.CharacterPalace.Service.Path;

public class JsonMapper {
	private static Gson gson;
	
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
	
	public static Hanzi[] jsonToHanzi(String json){
		if(gson==null) {
			gson = new Gson();			
		}
		System.out.println("Gson action");
		Hanzi[] result= gson.fromJson(json, Hanzi[].class);
		return result;
	}
	
	public static String entityToJson(CharacterEntity entity) {
		if(gson==null) {
			gson = new Gson();			
		}
		return gson.toJson(entity);
	}
	
	public static String identityPointsToJson(List<double[]>points) {
		if(gson==null) {
			gson = new Gson();			
		}
		return gson.toJson(points);
	}
	
	public static String objectToJson(Object o) {
		if(gson==null) {
			gson = new Gson();			
		}
		return gson.toJson(o);
	}
	public static KdTree JsonToKdTree(String json) {
		if(gson==null) {
			gson = new Gson();			
		}
		KdTree.Node root = gson.fromJson(json, KdTree.Node.class);
		KdTree kdTree = new KdTree(root);
		return kdTree;
	}
}
