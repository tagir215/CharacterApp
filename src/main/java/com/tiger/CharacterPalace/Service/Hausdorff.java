package com.tiger.CharacterPalace.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.util.CharacterUtil;
import com.tiger.CharacterPalace.util.JsonMapper;

public class Hausdorff {
	public static double hausdorffsDistance(KdTree kdTree, List<double[]>path) {
		double biggestDistance = 0;
		for(int i=0; i<path.size(); i++) {
			double[] point = new double[] {path.get(i)[0],path.get(i)[1]};
			double[] point2 = kdTree.nearestNeighbor(point);
			
			double dx = point2[0]-point[0];
			double dy = point2[1]-point[1];
			double dist = dx*dx + dy*dy;
			if(dist>biggestDistance) {
				biggestDistance = dist;
			}
		}
		return Math.sqrt(biggestDistance);
	}
	public static CharacterEntity findClosestMatch(KdTree kdTree) {
		return closestMatch(kdTree);
	}
	
	public static CharacterEntity findClosestMatch(CharacterEntity entity) {
		KdTree kdtree1 = JsonMapper.JsonToKdTree(entity.getIdentityData());
		List<double[]>points = kdtree1.getPointsAsList();
		return closestMatch(points);
	}
	
	private static CharacterEntity closestMatch(KdTree kdTree) {
		List<double[]>points1 = kdTree.getPointsAsList();
		HashMap<Integer,KdTree>kdTreeMap = CharacterUtil.getKdTreeMap();
		double smallestDistance = Double.MAX_VALUE;
		CharacterEntity closestMatch = null;
		for(Integer key : kdTreeMap.keySet()) {
			List<double[]>points2 = kdTreeMap.get(key).getPointsAsList();
			double distance1 = Hausdorff.hausdorffsDistance(kdTree, points2);	
			if(distance1 < smallestDistance) {
				smallestDistance = distance1;
				closestMatch = CharacterUtil.getEntityMap().get(key);
			}
		}
		return closestMatch;
	}
	
	
	private static CharacterEntity closestMatch(List<double[]>points) {
		
		HashMap<Integer,KdTree>kdTreeMap = CharacterUtil.getKdTreeMap();
		double smallestDistance = Double.MAX_VALUE;
		CharacterEntity closestMatch = null;
		for(Integer key : kdTreeMap.keySet()) {
			KdTree kdTree = kdTreeMap.get(key);
			double distance = Hausdorff.hausdorffsDistance(kdTree, points);
			
			if(distance < smallestDistance) {
				smallestDistance = distance;
				closestMatch = CharacterUtil.getEntityMap().get(key);
			}
		}
		return closestMatch;
	}
	
	public static List<CharacterEntity> closestMatches(KdTree kdTree) {
		List<CharacterEntity>result = new ArrayList<>();
		HashMap<Integer,KdTree>kdTreeMap = CharacterUtil.getKdTreeMap();
		CharacterEntity closestMatch = null;
		for(Integer key : kdTreeMap.keySet()) {
			List<double[]> points = kdTreeMap.get(key).getPointsAsList();
			double distance = Hausdorff.hausdorffsDistance(kdTree, points);
			
			if(distance < 55) {
				closestMatch = CharacterUtil.getEntityMap().get(key);
				result.add(closestMatch);
			}
		}
		return result;
	}
}
