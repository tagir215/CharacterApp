package com.tiger.CharacterPalace.service;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.PathPoint;
import com.tiger.CharacterPalace.service.character.IdentityCreator;
import com.tiger.CharacterPalace.service.character.IdentityCreatorI;
import com.tiger.CharacterPalace.service.character.PathCreator;
import com.tiger.CharacterPalace.util.FileManager;
import com.tiger.CharacterPalace.util.ImageUtils;
import com.tiger.CharacterPalace.util.ZhangSuen;

public class PathCreatorTest {
    private BufferedImage image;
    double[][] points1;
    double[][] points2;
    @BeforeEach
    public void setUp() {
        int width = 10;
        int height = 10;
        image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for(int x=0; x<width; x++) {
        	for(int y=0; y<height; y++) {
        		image.setRGB(x, y, 0xFFFFFF);
        		
        	}
        }
        points1 = new double[][] {
        	new double[] {2,2},
        	new double[] {3,2},
        	new double[] {4,2},
        	new double[] {5,2},
        	new double[] {6,2},
        	new double[] {4,3},
        	new double[] {4,4},
        	new double[] {4,5},
        	new double[] {4,6}
        };
        for(double[] point : points1) {
        	image.setRGB((int)point[0], (int)point[1], 0);        	
        }
        points2 = new double[][] {
        	new double[] {8,2},
        	new double[] {8,3},
        	new double[] {8,4},
        	new double[] {8,5},
        	new double[] {8,6},
        };
        for(double[] point : points2) {
        	image.setRGB((int)point[0], (int)point[1], 0);        	
        }
    }

    @Test
    public void testImageToPaths() {
        List<List<PathPoint>> paths = PathCreator.imageToPaths(image);
        Assertions.assertNotNull(paths);
        Assertions.assertFalse(paths.isEmpty());
       
        List<PathPoint>path = paths.get(0);
        for(int i=0; i<path.size(); i++) {
        	Assertions.assertEquals(points1[i][0], path.get(i).getPoint()[0],1);
          	Assertions.assertEquals(points1[i][1], path.get(i).getPoint()[1],1);
        }
        path = paths.get(1);
        for(int i=0; i<path.size(); i++) {
        	Assertions.assertEquals(points2[i][0], path.get(i).getPoint()[0],1);
          	Assertions.assertEquals(points2[i][1], path.get(i).getPoint()[1],1);
        }
       
    }
    
    @Test
    public void displayShow() throws InterruptedException{
    	String path = FileManager.getResourcePath("DroidSansFallback.ttf");
    	//String path = FileManager.getResourcePath("HanyiSentyRubber.ttf");
    	//String path = FileManager.getResourcePath("MaShanZheng-Regular.ttf");
    	//String path = FileManager.getResourcePath("XiaolaiMonoSC-Regular.ttf");
    	//String path = FileManager.getResourcePath("zhs-caonima-ti.ttf");
    	Font font = null;
    	int scale = 3;
    	int sleepTime = 2000;
    	int unicode = 22810;
    	//int unicode = 23391;
    	try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	int size = 200;
    	BufferedImage image = ImageUtils.fontToImages(unicode, font, size);
    	ImageUtils.displayImage(image, scale);
    	Thread.sleep(sleepTime);
    	
    	ZhangSuen.skeletonize(image);
    	ImageUtils.displayImage(image, scale);
    	Thread.sleep(sleepTime);
    
    	List<List<PathPoint>>paths = PathCreator.imageToPaths(image);
    	ImageUtils.displayImage(ImageUtils.pathsToImage(paths, size), scale);
    	Thread.sleep(sleepTime);
    	
    	
    	IdentityCreator creator = new IdentityCreatorI();
    	List<List<double[]>>identityPoints = creator.pathsIdentityPoints(paths);
    	
    	ImageUtils.displayImage(ImageUtils.pointsToImage(identityPoints, size),scale);
    	Thread.sleep(sleepTime);
    }
    
}
