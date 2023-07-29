package com.tiger.CharacterPalace.service.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.tiger.CharacterPalace.model.PathPoint;

public class IdentityCreatorI implements IdentityCreator{
	private static final int FILTER_RATIO_DIVIDER = 14;
	private static final int VECTOR_LENGTH = 6;
	private static final int STROKE_DIST = 10;
	
	@Override
	public List<List<double[]>> pathsIdentityPoints(List<List<PathPoint>> paths) {
		return filterPathsByRatio(paths);
	}
	
	
	public List<double[]>createDirectionList2(List<PathPoint>path){
		List<double[]>directions = new ArrayList<>();
		List<double[]>sortedKeyPoints = getSortedKeyPoints(path);
		double[]prevPoint = null;
		for(int i=0; i<sortedKeyPoints.size(); i++) {
		
			
			double[]point1=prevPoint;
			double[]point2=sortedKeyPoints.get(i);
			prevPoint = point2;
			if(point1==null) {
				continue;
			}

			double dx = point2[0]-point1[0];
			double dy = point2[1]-point1[1];
			double[]dir = new double[2];
			if(dx == 0) {
				dir[0]=0;
			}else {
				dir[0]=dx > 0 ? 1 : -1;
			}
			if(dy == 0) {
				dir[1]=0;	
			}else {
				dir[1]= dy > 0 ? 1 : -1;
			}
			directions.add(dir);
		}
		return directions;
	}
	
	public List<double[]>getSortedKeyPoints(List<PathPoint>iPoints){
		HashMap<Double,double[]>pointDistanceMap = new HashMap<>();
		
		for(PathPoint p : iPoints) {
			if(p.isKeyPoint()) {
				double distanceFromZero = p.getPoint()[0]*p.getPoint()[0] + p.getPoint()[1]+p.getPoint()[1];
				pointDistanceMap.put(distanceFromZero, p.getPoint());
			}
		}
		List<Double>distances = new ArrayList<>();
		for(Double dist : pointDistanceMap.keySet()) {
			distances.add(dist);
		}
		Collections.sort(distances);
		List<double[]>keyPointsSorted = new ArrayList<>();
		for(int i=0; i<distances.size(); i++) {
			keyPointsSorted.add(pointDistanceMap.get(distances.get(i)));
		}
		return keyPointsSorted;
	}
	
	
	public List<double[]> createDirectionList(List<PathPoint>path) {
		List<double[]>directions = new ArrayList<>();
		List<List<double[]>>strokes = dividePathToStrokes(path);
		for(List<double[]>stroke : strokes) {
			double[] lastDir = new double[2];
			for(int i=VECTOR_LENGTH; i<stroke.size(); i+=VECTOR_LENGTH) {
				double[] point1 = stroke.get(i-VECTOR_LENGTH);
				double[] point2 = stroke.get(i);
				double dx = point2[0]-point1[0];
				double dy = point2[1]-point1[1];
				double[]dir = new double[2];
				if(dx == 0) {
					dir[0]=0;
				}else {
					dir[0]=dx > 0 ? 1 : -1;
				}
				if(dy == 0) {
					dir[1]=0;	
				}else {
					dir[1]= dy > 0 ? 1 : -1;
				}
				if(Arrays.equals(dir, lastDir)) {
					if(directions.size()==0) {
						directions.add(dir);
					}
					else if(!Arrays.equals(directions.get(directions.size()-1), dir)) {
						directions.add(dir);											
					}
				}
				lastDir = dir;
				
			}
		}
		return directions;
	}
	private List<List<double[]>>dividePathToStrokes(List<PathPoint>path){
		List<List<double[]>>strokes = new ArrayList<>();
		strokes.add(new ArrayList<>());
		for(int i=1; i<path.size(); i++) {
			double[] point1 = path.get(i-1).getPoint();
			double[] point2 = path.get(i).getPoint();
			double dx = point2[0]-point1[0];
			double dy = point2[1]-point1[1];
			double dist = dx*dx+dy*dy;
			if(dist>STROKE_DIST) {
				strokes.add(new ArrayList<>());
			}
			strokes.get(strokes.size()-1).add(point2);
		}
		return strokes;
	}
	
	
	public List<List<double[]>> filterPathsByRatio(List<List<PathPoint>>paths) {
		List<List<double[]>>filteredPaths = new ArrayList<>();
		for(List<PathPoint>path : paths) {
			double leftX = Double.MAX_VALUE;
			double rightX = 0;
			for(PathPoint point : path) {
				if(point.getPoint()[0]<leftX) leftX = point.getPoint()[0];
				if(point.getPoint()[0]>rightX) rightX = point.getPoint()[0];
			}
			double width = rightX - leftX;
			int filterRatio = (int)width/FILTER_RATIO_DIVIDER;
			if(filterRatio<1) filterRatio = 1;
			List<double[]>filtered = filterByRatio(path,filterRatio);
			filteredPaths.add(filtered);
		}
		return filteredPaths;
	}
	private List<double[]> filterByRatio(List<PathPoint>path,int filterRatio) {
		List<double[]>filtered = new ArrayList<>();
		for(int i=0; i<path.size(); i++) {
			if(i%filterRatio==0) {
				filtered.add(path.get(i).getPoint());
			}
		}
		return filtered;
	}
	

}
