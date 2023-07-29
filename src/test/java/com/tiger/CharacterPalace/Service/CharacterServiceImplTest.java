package com.tiger.CharacterPalace.service;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.KdTree;
import com.tiger.CharacterPalace.model.PathPoint;
import com.tiger.CharacterPalace.service.character.Hausdorff;
import com.tiger.CharacterPalace.service.character.IdentityCreator;
import com.tiger.CharacterPalace.service.character.IdentityCreatorI;
import com.tiger.CharacterPalace.service.character.PathCreator;
import com.tiger.CharacterPalace.util.Constants;
import com.tiger.CharacterPalace.util.FileManager;
import com.tiger.CharacterPalace.util.ImageUtils;
import com.tiger.CharacterPalace.util.ZhangSuen;

class CharacterServiceImplTest {
	
	List<KdTree> kdTrees;
	List<KdTree>partKdTrees;
	BufferedImage image;
	@BeforeEach
	void setUp() {
		int[] unicodes = {
				20063,
				20182,
				23232,
				34343,
		};
		String path = FileManager.getResourcePath("zhs-caonima-ti.ttf");
		Font font = FileManager.createFont(path);
		IdentityCreator iCreator = new IdentityCreatorI();
		kdTrees = new ArrayList<>();
		partKdTrees = new ArrayList<>();
		for(int i=0; i<unicodes.length; i++) {
			BufferedImage image = ImageUtils.fontToImages(unicodes[i], font, Constants.IMAGE_SIZE);
			ZhangSuen.skeletonize(image);
			List<List<PathPoint>>paths = PathCreator.imageToPaths(image);
			List<List<double[]>>ip = iCreator.pathsIdentityPoints(paths);
			if(i==0) {				
				for(List<double[]>p : ip) {
					partKdTrees.add(new KdTree(p));
				}
				
			}
			List<double[]>points = new ArrayList<>();
			for(List<double[]>p : ip) {
				points.addAll(p);
			}
			kdTrees.add(new KdTree(points));
		}
	}

	@Test
	void test() {
		
		double dist = Hausdorff.getHausdorffsDistance(kdTrees.get(0), kdTrees.get(0));
		System.out.println(dist);
	}

}
