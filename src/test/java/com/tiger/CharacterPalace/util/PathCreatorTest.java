package com.tiger.CharacterPalace.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        	new double[] {2,2,0},
        	new double[] {3,2,0},
        	new double[] {4,2,0},
        	new double[] {5,2,0},
        	new double[] {6,2,90},
        	new double[] {4,3,90},
        	new double[] {4,4,90},
        	new double[] {4,5,90},
        	new double[] {4,6,90}
        };
        for(double[] point : points1) {
        	image.setRGB((int)point[0], (int)point[1], 0);        	
        }
        points2 = new double[][] {
        	new double[] {8,2,0},
        	new double[] {8,3,0},
        	new double[] {8,4,0},
        	new double[] {8,5,0},
        	new double[] {8,6,0},
        };
        for(double[] point : points2) {
        	image.setRGB((int)point[0], (int)point[1], 0);        	
        }
    }

    @Test
    public void testImageToPaths() {
        List<List<double[]>> paths = PathCreator.imageToPaths(image);
        Assertions.assertNotNull(paths);
        Assertions.assertFalse(paths.isEmpty());
       
        double[][] path1 = new double[paths.get(0).size()][];
        for (int i = 0; i < paths.get(0).size(); i++) {
            path1[i] = paths.get(0).get(i);
            System.out.println(path1[i][0]+" "+path1[i][1]+" "+path1[i][2]);
        }
        System.out.println(" ");
        double[][] path2 = new double[paths.get(1).size()][];
        for (int i = 0; i < paths.get(1).size(); i++) {
            path2[i] = paths.get(1).get(i);
        }
        
        Assertions.assertArrayEquals(points1, path1);
        Assertions.assertArrayEquals(points2, path2);
    }
    
    @Test
    public void testNormalizePath() {
        List<double[]> path = new ArrayList<>();
        path.add(new double[]{1, 5});
        path.add(new double[]{5, 5});
        path.add(new double[]{9, 5});

        float size = 100.0f;

        PathCreator.normalizePath(path, size);

        double[] ep = {10,50};
        double[] ep2 = {50,50};
        double[] ep3 = {90,50};
        Assertions.assertArrayEquals(ep, path.get(0));
        Assertions.assertArrayEquals(ep2, path.get(1));
        Assertions.assertArrayEquals(ep3, path.get(2));
        
        
    }
    
    @Test
    public void displayShow() throws InterruptedException{
    	String path = FileManager.getResourcePath("DroidSansFallback.ttf");
    	Font font = null;
    	int scale = 3;
    	int sleepTime = 2000;
    	int unicode = 23391;
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
    
    	List<List<double[]>>paths = PathCreator.imageToPaths(image);
    	
    	ImageUtils.displayImage(ImageUtils.pathsToImage(paths, size), scale);
    	Thread.sleep(sleepTime);
    	
    	PathCreator.filterPathsByRatio(paths,Constants.FILTER_RATIO);
    	
    	ImageUtils.displayImage(ImageUtils.pathsToImage(paths, size), scale);
    	Thread.sleep(sleepTime);
    	
    	for(int i=0; i<paths.size(); i++) {
    		PathCreator.normalizePath(paths.get(i), size);
    		List<List<double[]>>paths2 = new ArrayList<>();
    		paths2.add(paths.get(i));
    		BufferedImage image2 = ImageUtils.pathsToImage(paths2, size);
        	ImageUtils.displayImage(image2, scale);
        	Thread.sleep(sleepTime);
        	
    	}
    	
    
    }
}
