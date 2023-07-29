package com.tiger.CharacterPalace.service.character;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.tiger.CharacterPalace.model.PathPoint;

public class PathCreator {
	private static int[] OFFSET_X = {1,1,1,0,-1,-1,-1,0,1};
	private static int[] OFFSET_Y = {-1,0,1,1,1,0,-1,-1,-1};
	
	public static List<List<PathPoint>> imageToPaths(BufferedImage image){
		List<List<PathPoint>>paths = new ArrayList<>();
		boolean[][] visited = new boolean[image.getWidth()][image.getHeight()];
		for(int y=1; y<image.getWidth()-1; y++) {
			for(int x=1; x<image.getHeight()-1; x++) {
				if((image.getRGB(x, y) & 0xFF) == 0 && !visited[x][y]) {
					List<PathPoint>path = new ArrayList<>();
					pathTraveller(path,new PathPoint(new double[] {x,y}),image,visited);
					paths.add(path);
				}
			}
		}
		
		return paths;
		
	}
	
	public static List<double[]>pathPointsToDoublePath(List<PathPoint>path){
		List<double[]>newPath = new ArrayList<>();
		for(PathPoint p : path) {
			newPath.add(p.getPoint());
		}
		return newPath;
	}
	
	
	public static List<double[]> filterKeyPoints(List<List<PathPoint>>paths) {
		List<double[]>points = new ArrayList<>();
		for(int i=0; i<paths.size(); i++) {
			List<PathPoint>path = paths.get(i);
			for(PathPoint p : path) {
				if(p.isKeyPoint()) points.add(p.getPoint());
			}
		}
		return points;
	}
	
	private static void pathTraveller(List<PathPoint>path,PathPoint startPoint, BufferedImage image, boolean[][] visited) {
		Queue<PathPoint> queue = new LinkedList<>();
		queue.add(startPoint);
		
		while(!queue.isEmpty()) {
			PathPoint point = queue.poll();
			int x = (int)point.getPoint()[0];
			int y = (int)point.getPoint()[1];
			if(!visited[x][y]) {
				visited[x][y] = true;	
				path.add(point);
			}
			int whiteToBlackTransitions= 0;
			boolean white = false;
			Queue<PathPoint> queue2 = new LinkedList<>();
			for(int i=0; i<OFFSET_X.length; i++) {
				int x1 = x+ OFFSET_X[i];
				int y1 = y+ OFFSET_Y[i];
				
				if(x1<0||x1>=visited.length || y1<0 || y1>=visited.length)
					continue;
				
				if((image.getRGB(x1,y1) & 0xFF) == 0) {
					if(!visited[x1][y1]) {
						queue2.add(new PathPoint (new double[]{x1,y1}));						
					}
					if(white) {
						whiteToBlackTransitions++;
					}
					
					white = false;
				}else {
					white = true;
				}
			}
			queue2.addAll(queue);
			queue = queue2;
			if(whiteToBlackTransitions==1 || whiteToBlackTransitions>2) {
				point.setKeyPoint(true);
			}
		}
	}
	
	

	
		
}

