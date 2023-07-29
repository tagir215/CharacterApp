package com.tiger.CharacterPalace.service.character;

import com.tiger.CharacterPalace.model.KdTree;

public class KdTreeNormalizer {
	public static void normalizeTrees(KdTree controlTree,KdTree transformingTree) {
		if(controlTree.getLeftX()!=0 || controlTree.getTopY()!=0) {
			controlTree.transform(-controlTree.getLeftX(), -controlTree.getTopY(), 1, 1);
		}
		transformingTree.transform(-transformingTree.getLeftX(), -transformingTree.getTopY(), 1, 1);
		double scaleX = controlTree.getWidth() / transformingTree.getWidth();
		double scaleY = controlTree.getHeight() / transformingTree.getHeight();
		transformingTree.transform(0, 0, scaleX, scaleY);
	}
	
	
}
