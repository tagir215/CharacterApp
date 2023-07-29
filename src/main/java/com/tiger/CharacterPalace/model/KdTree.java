package com.tiger.CharacterPalace.model;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.tiger.CharacterPalace.util.MySort;

public class KdTree {
	private Node root;
	private double distance;
	private Node nearest;
	private int direction;
	
	private double width;
	private double height;
	private int leftX = Integer.MAX_VALUE;
	private int rightX =0;
	private int bottomY = 0;
	private int topY = Integer.MAX_VALUE;
	
	
	public KdTree(List<double[]>points) {
		if(points.size()>0) {
			root = new Node(points,0);			
		}
	}
	public KdTree(Node root) {
		this.root = root;
	}
	
	public class Node{
		@SerializedName("point")
		double[] point = new double[2];
		@SerializedName("children")
		Node[] children = new Node[2];	

		public Node(List<double[]>points,int d) {
			createKdTree(points,d);
		}
		
		private void createKdTree(List<double[]>points,int d) {
			int axis = d%2;
			int perpAxis = Math.abs(axis-1);
			MySort.mergeSort(points, perpAxis);
			int pivot = points.size()/2;
			point[0] = points.get(pivot)[0];
			point[1] = points.get(pivot)[1];
			double pivotValue = points.get(pivot)[perpAxis];
			points.remove(pivot);
			
			//StringBuilder stringBuilder = new StringBuilder();
			//for(int i=0; i<d*2; i++) {
			//	if(direction>0) {
			//		stringBuilder.append("-");					
			//	}else {
			//		stringBuilder.append(".");
			//	}
			//}
			//System.out.println(stringBuilder.toString() +"("+point[0]+","+point[1]+")");	
			
			if(points.size()<=0) {
				return;
			}
			
			List<double[]>left = new ArrayList<>();
			List<double[]>right = new ArrayList<>();
			for(int i=0; i<points.size(); i++) {
				if(points.get(i)[perpAxis]<pivotValue)
					left.add(points.get(i));
				else
					right.add(points.get(i));
			}
			
			direction  = -1;
			if(left.size()>0) {
				children[0] = new Node(left,d+1);				
			}
			direction = 1;
			if(right.size()>0) {				
				children[1] = new Node(right,d+1);
			}
		}
		
	}
	
	public void calculateDimensions() {
		List<double[]>path = getPointsAsList();
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
		width = rightX - leftX;
		height = bottomY - topY;
	}
	
	
	
	public double[] nearestNeighbor(double[] point) {
		if(root==null) {
			return null;
		}
		distance = Double.MAX_VALUE;
		nearest = null;
		getNearestNeighbor(point,root,1);
		if(nearest!=null)
			return nearest.point;
		else return root.point;
	}
	
	private void getNearestNeighbor(double[] point, Node node, int d) {
		if(node ==null) {
			return;
		}
		
		double dx = Math.abs(point[0] - node.point[0]);
		double dy = Math.abs(point[1] - node.point[1]);
		double dist= dx*dx + dy*dy;
		int axis = d%2;		
		int perpAxis = Math.abs(axis-1);
		
		if(node.children[0]==null && node.children[1] == null) {
			if(dist<distance) {
				distance = dist;
				nearest = node;
			}
			return;
		}
		
		if(node.children[0]!=null && point[axis]<node.point[axis]) {	
			getNearestNeighbor(point,node.children[0],d+1);
		}else {
			getNearestNeighbor(point,node.children[1],d+1);
		}
		
		if(dist<distance) {
			distance = dist;	
			nearest = node;
		}
		double l =Math.abs(point[perpAxis] - node.point[perpAxis]);
		if(l*l<distance) {	
			Node c = node.children[perpAxis];
			if(c==null) {
				return;
			}
			double dx2 = Math.abs(point[0]-c.point[0]);
			double dy2 = Math.abs(point[1]-c.point[1]);
			double dist2 = Math.abs(dx2*dx2 + dy2*dy2);
			if(dist2<distance) {
				distance = dist2;
				nearest = node.children[perpAxis];
			}
		}
		
	}
	
	public Node getRoot() {
		return root;
	}
	
	public List<double[]> getPointsAsList() {
	        List<double[]> pointsList = new ArrayList<>();
	        getPointsAsList(root, pointsList);
	        return pointsList;
	}
	
	public void transform(double translationX, double translationY, double scaleX, double scaleY) {
		if(scaleX!=1 || scaleY!=1) {
			scale(root,scaleX,scaleY);
			leftX*=scaleX; rightX*=scaleX; topY*=scaleY; bottomY*=scaleY;
			width*=scaleX;
			height*=scaleY;
		}
		if(translationX!=0 || translationY!=0) {
			translate(root,translationX,translationY);	
			leftX+=translationX; rightX+=translationX; topY+=translationY; bottomY+=translationY;
		}
	}
	
	private void translate(KdTree.Node node,double translationX, double translationY) {
		if(node == null) return;
		node.point[0]+=translationX;
		node.point[1]+=translationY;
		translate(node.children[0],translationX,translationY);
		translate(node.children[1],translationX,translationY);
	}
	private void scale(Node node,double scaleX, double scaleY) {
		if(node == null) return;
		node.point[0]*=scaleX;
		node.point[1]*=scaleY;
		scale(node.children[0],scaleX,scaleY);
		scale(node.children[1],scaleX,scaleY);
	}

    private void getPointsAsList(Node node, List<double[]> pointsList) {
        if (node == null) {
            return;
        }

        pointsList.add(node.point);
        getPointsAsList(node.children[0], pointsList);
        getPointsAsList(node.children[1], pointsList);
    }
    
    
    
	public double getDistance() {
		return distance;
	}
	public Node getNearest() {
		return nearest;
	}
	public int getDirection() {
		return direction;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	public int getLeftX() {
		return leftX;
	}
	public int getRightX() {
		return rightX;
	}
	public int getBottomY() {
		return bottomY;
	}
	public int getTopY() {
		return topY;
	}
	
    
    
}
