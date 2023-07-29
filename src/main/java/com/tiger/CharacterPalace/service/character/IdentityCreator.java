package com.tiger.CharacterPalace.service.character;

import java.util.List;

import com.tiger.CharacterPalace.model.PathPoint;

public interface IdentityCreator {
	public List<List<double[]>> pathsIdentityPoints(List<List<PathPoint>>paths);
	
}
