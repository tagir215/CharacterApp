package com.tiger.CharacterPalace.Service;

import java.util.ArrayList;
import java.util.List;

public class MyShape {
	private List<Path>curves = new ArrayList<>();
	private int unicode;
	
	
	
	public MyShape(int unicode) {
		super();
		this.unicode = unicode;
	}

	public int getUnicode() {
		return unicode;
	}

	public void setUnicode(int unicode) {
		this.unicode = unicode;
	}

	public void addCurve(Path curve) {
		curves.add(curve);
	}

	

	public List<Path> getCurves() {
		return curves;
	}

	
	
}
