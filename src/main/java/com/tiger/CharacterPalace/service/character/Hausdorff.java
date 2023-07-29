package com.tiger.CharacterPalace.service.character;

import java.util.List;

import com.tiger.CharacterPalace.model.KdTree;

public class Hausdorff {
	public static double getHausdorffsDistance(KdTree kdTree, KdTree kdTree2) {
		double dist1 = hausdorffsDistance(kdTree,kdTree2.getPointsAsList(),false);
		double dist2 = hausdorffsDistance(kdTree2,kdTree.getPointsAsList(),false);
		double bigger = dist1 > dist2 ? dist1 : dist2;
		if(Double.isNaN(dist1) || Double.isNaN(dist2)) {
			dist2 = hausdorffsDistance(kdTree2,kdTree.getPointsAsList(),true);
		}
		return bigger;
	}
	private static double hausdorffsDistance(KdTree kdTree, List<double[]>path,boolean print) {
		double biggestDistance = 0;
		for(int i=0; i<path.size(); i++) {
			double[] point = path.get(i);
			double[] point2 = kdTree.nearestNeighbor(point);
			if(point2==null)
				continue;
			double dx = point2[0]-point[0];
			double dy = point2[1]-point[1];
			double dist = dx*dx + dy*dy;
			if(print) {
				System.out.println("p1:"+point[0]+","+point[1]+ " p2:"+point2[0]+","+point2[1]+" dx:"+dx+" dy:"+dy+" dist:"+dist);
			}
			if(dist>biggestDistance) {
				biggestDistance = dist;
			}
		}
		return Math.sqrt(biggestDistance);
	}
	
}
