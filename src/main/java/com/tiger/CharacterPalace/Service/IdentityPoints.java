package com.tiger.CharacterPalace.Service;

import java.util.ArrayList;
import java.util.List;

public class IdentityPoints {
	List<double[]>points = new ArrayList<>();
	

	public List<double[]> getPoints() {
		return points;
	}

	public void addPoint(double[] point) {
		points.add(point);
	}
	
}
