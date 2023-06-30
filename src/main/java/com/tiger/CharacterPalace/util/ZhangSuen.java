package com.tiger.CharacterPalace.util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ZhangSuen {
    private static int[] OFFSETS_X = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] OFFSETS_Y = {-1, -1, -1, 0, 0, 1, 1, 1};
    
	

    public static void skeletonize(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int step = 1;
        while (true) {
            boolean changed = false;
            List<int[]>pointsToChange = new ArrayList<>();
            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                    int pixel = image.getRGB(x, y) & 0xFF;
                    if (pixel == 0) {
                    	int nCount = neighborCount(x, y, image);
                        if (nCount > 1 && nCount < 7 && whiteToBlackTransitionCount(x, y, image) == 1 && checkConnectivity(x, y, image, step)) {
                            pointsToChange.add(new int[] {x,y});
                            changed = true;
                        }
                    }
                }
            }
            for(int[] point : pointsToChange) {
            	image.setRGB(point[0], point[1], 0xFFFFFFFF);
            }
            if (step == 1) {
                step = 2;
            } else {
                step = 1;
            }
            if (!changed) {
                break;
            }
        }
    }

    public static int neighborCount(int x, int y, BufferedImage bitmap) {
        int count = 0;

        for (int i = 0; i < OFFSETS_X.length; i++) {
            int offsetX = OFFSETS_X[i];
            int offsetY = OFFSETS_Y[i];
            int pixel = bitmap.getRGB(x + offsetX, y + offsetY) & 0xFF;
            if (pixel != 255) {
                count++;
            }
        }
        return count;
    }

    private static int whiteToBlackTransitionCount(int x, int y, BufferedImage bitmap) {
        int[][] offsets = {
                {-1, -1}, {0, -1}, {1, -1},
                {1, 0}, {1, 1}, {0, 1},
                {-1, 1}, {-1, 0}, {-1, -1}
        };
        int count = 0;
        boolean white = false;
        for (int[] offset : offsets) {
            int offsetX = offset[0];
            int offsetY = offset[1];
            int pixel = bitmap.getRGB(x + offsetX, y + offsetY) & 0xFF;
            if (pixel == 0) {
                if (white) {
                    count++;
                    white = false;
                }
            } else {
                white = true;
            }
        }
        return count;
    }

    private static boolean checkConnectivity(int x, int y, BufferedImage bitmap, int step) {
        if (step == 1) {
            if ((bitmap.getRGB(x,y-1) & 0xFF)==0  && (bitmap.getRGB(x+1,y) & 0xFF)==0 && (bitmap.getRGB(x,y+1) & 0xFF)==0){
                return false;
            }
            if ((bitmap.getRGB(x+1,y) & 0xFF)==255  || (bitmap.getRGB(x,y+1) & 0xFF)==255 || (bitmap.getRGB(x-1,y) & 0xFF)==255){
                return true;
            }
            return false;
        } else {
        	if ((bitmap.getRGB(x,y-1) & 0xFF)==0  && (bitmap.getRGB(x+1,y) & 0xFF)==0 && (bitmap.getRGB(x-1,y) & 0xFF)==0){
                return false;
            }
            if ((bitmap.getRGB(x-1,y) & 0xFF)==255  || (bitmap.getRGB(x,y+1) & 0xFF)==255 || (bitmap.getRGB(x,y-1) & 0xFF)==255){
                return true;
            }
            return false;
        }
    }
	   
}
