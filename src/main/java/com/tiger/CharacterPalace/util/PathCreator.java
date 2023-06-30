package com.tiger.CharacterPalace.util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PathCreator {
	public static List<List<double[]>> imageToPaths(BufferedImage image){
		List<List<double[]>>paths = new ArrayList<>();
		boolean[][] visited = new boolean[image.getWidth()][image.getHeight()];
		for(int x=1; x<image.getWidth()-1; x++) {
			for(int y=1; y<image.getHeight()-1; y++) {
				if((image.getRGB(x, y) & 0xFF) == 0 && !visited[x][y]) {
					List<double[]>path = new ArrayList<>();
					pathTraveller(path,new double[] {x,y,0},image,visited);
					paths.add(path);
				}
			}
		}
		
		return paths;
		
	}
	private static void pathTraveller(List<double[]>path,double[] rootNode, BufferedImage image, boolean[][] visited) {
		Stack<double[]> stack = new Stack<>();
		stack.add(rootNode);
		int[] offsetX = {-1,0,1,1,1,0,-1,-1};
		int[] offsetY = {-1,-1,-1,0,1,1,1,0};
		while(!stack.isEmpty()) {
			double[] point = stack.pop();
			int x = (int)point[0];
			int y = (int)point[1];
			if(!visited[x][y]) {
				visited[x][y] = true;	
				path.add(point);
				if(path.size()>1) {
					addAngle(path.get(path.size()-1),path.get(path.size()-2));
				}
			}
			for(int i=0; i<offsetX.length; i++) {
				int x1 = x+ offsetX[i];
				int y1 = y+ offsetY[i];
				if(!visited[x1][y1] && (image.getRGB(x1,y1) & 0xFF) == 0) {
					stack.add(new double[] {x1,y1,0});
				}
			}
		}
	}
	
	private static void addAngle(double[]point,double[]prevPoint) {
		double dx = point[0]-prevPoint[0];
		double dy = point[1]-prevPoint[1];
		double rad = Math.atan2(dy, dx);
		double degrees = Math.toDegrees(rad);
		if(degrees<0) {
			degrees+=360;
		}
		point[2] = degrees;
	}
	
	private static void pathTraveller2(List<double[]>path,double[] point,BufferedImage image, boolean[][] visited) {
		path.add(point);
		int x = (int)point[0];
		int y = (int)point[1];
		visited[x][y] = true;
		int[] offsetX = {-1,0,1,1,1,0,-1,-1};
		int[] offsetY = {-1,-1,-1,0,1,1,1,0};
		for(int i=0; i<offsetX.length; i++) {
			int x1 = x+ offsetX[i];
			int y1 = y+ offsetY[i];
			if(!visited[x1][y1] && (image.getRGB(x1,y1) & 0xFF) == 0) {
				pathTraveller(path,new double[] {x1,y1},image,visited);
			}
		}
		
	}
	
	public static void normalizePath(List<double[]>path,float size) {
		int leftX = Integer.MAX_VALUE;
		int rightX =0;
		int bottomY = 0;
		int topY = Integer.MAX_VALUE;
		for(int i=0; i<path.size(); i++) {
			int x = (int)path.get(i)[0];
			int y = (int)path.get(i)[1];
			
			if(x<leftX) {
				leftX = x;
			}
			if(x>rightX) {
				rightX = x;
			}
			if(y>bottomY) {
				bottomY = y;
			}
			if(y<topY) {
				topY = y;
			}
		}
		
		int width = rightX - leftX;
		int height = bottomY - topY;
		float targetSize = 0.8f*size;
		
		float scale = 0;
		if(width>height) {
			scale = targetSize / width;
		}else {
			scale = targetSize / height;
		}
		
		
		translatePath(-leftX,-topY,path);
		scalePath(scale,path);	
		
		height = (int)(height * scale);
		width = (int)(width * scale);
		
		int offsetX = (int)(size/2-width/2);
		int offsetY = (int)(size/2-height/2);
		translatePath(offsetX,offsetY,path);
	}
	
	private static void translatePath(int translationX, int translationY, List<double[]>path) {
		for(double[] point : path) {
			point[0]+=translationX;
			point[1]+=translationY;
		}
	}
	private static void scalePath(float scale, List<double[]>path) {
		for(double[] point : path) {
			point[0]*=scale;
			point[1]*=scale;
		}
	}
	
	public static void filterKeyPoints(List<List<double[]>>paths){
		for(int i=0; i<paths.size(); i++) {
			List<double[]>path = paths.get(i);
			
			double pdx = 0;
			double pdy = 0;
			int	l = 1;
			List<double[]>keyPoints = new ArrayList<>();
			
			for(int i2=l; i2<path.size(); i2+=l){
				
				double dx = path.get(i2)[0]-path.get(i2-l)[0];
				double dy = path.get(i2)[1]-path.get(i2-l)[1];
				double dotProduct = (dx * pdx + dy * pdy);
				double mag1 =(int) Math.sqrt(pdx*pdx + pdy*pdy);
				double mag2 =(int) Math.sqrt(dx*dx + dy*dy);
				double cosineSimilarity = dotProduct / mag1 * mag2;
				
				if(cosineSimilarity<0.7 || (pdx == 0 && pdy == 0)) {
					keyPoints.add(path.get(i2-l));
				}
				pdx = dx;
				pdy = dy;
			}
			keyPoints.add(path.get(path.size()-1));
			paths.set(i, keyPoints);
		}
		
	}
	
	public static void filterPathsByRatio(List<List<double[]>>paths,int filterRatio) {
		for(int i=0; i<paths.size(); i++) {
			List<double[]>path = paths.get(i);
			List<double[]>keyPoints = new ArrayList<>();
			
			for(int i2=0; i2<path.size(); i2+=filterRatio){
				keyPoints.add(path.get(i2));
				if(i2+filterRatio>path.size()-1) {
					i2 = path.size()-1;
				}
			}
			paths.set(i, keyPoints);
		}
		
	}
		
}
