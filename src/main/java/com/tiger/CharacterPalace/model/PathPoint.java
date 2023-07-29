package com.tiger.CharacterPalace.model;

public class PathPoint {
	private double[] point;
	private boolean keyPoint;
	
	
	public PathPoint(double[] point) {
		super();
		this.point = point;
	}
	
	public double[] getPoint() {
		return point;
	}
	public void setPoint(double[] point) {
		this.point = point;
	}
	public boolean isKeyPoint() {
		return keyPoint;
	}
	public void setKeyPoint(boolean keyPoint) {
		this.keyPoint = keyPoint;
	}
	
	
}
