package com.tiger.CharacterPalace.model;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private String type;
	private List<double[]>coordinates = new ArrayList<>();
	

	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public List<double[]> getCoordinates() {
		return coordinates;
	}
	public void addCoordinate(double[] coord) {
		coordinates.add(coord);
	}
}
