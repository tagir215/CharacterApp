package com.tiger.CharacterPalace.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.tiger.CharacterPalace.model.PathPoint;

public class ImageUtils {
    public static BufferedImage fontToImages(int codePoint,Font font,float size) {
    	Font scaledFont = font.deriveFont(size);
    	
        if (Character.isValidCodePoint(codePoint) && scaledFont.canDisplay(codePoint)) {
        	BufferedImage image = new BufferedImage((int)size,(int)size,BufferedImage.TYPE_BYTE_BINARY);
        	Graphics2D g2d = image.createGraphics();
            
            FontRenderContext frc = g2d.getFontRenderContext();
            String character = new String(Character.toChars(codePoint));
            GlyphVector glyphVector = scaledFont.createGlyphVector(frc, character); 
            
            AffineTransform transform = AffineTransform.getTranslateInstance(0, image.getHeight()/1.2);
            Shape glyphShape = transform.createTransformedShape(glyphVector.getOutline());
            
        	Area glyphArea = new Area(glyphShape);
        	g2d.setBackground(Color.WHITE);
        	g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
        	g2d.setColor(Color.BLACK);
        	g2d.fill(glyphArea);
        	g2d.dispose();
        	return image;
         }
	    
	    return null;
    }
    
    public static void displayImage(BufferedImage image, int scale) {
        int width = image.getWidth() * scale;
        int height = image.getHeight() * scale;

        BufferedImage stretchedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = stretchedImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        JFrame frame = new JFrame("Stretched Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(new ImageIcon(stretchedImage));
        frame.getContentPane().add(label);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static BufferedImage pathsToImage(List<List<PathPoint>>paths, int size) {
    	List<List<double[]>>doublePaths = new ArrayList<>();
    	for(List<PathPoint>path : paths) {
    		List<double[]>points = new ArrayList<>();
    		for(PathPoint point : path) {
    			points.add(point.getPoint());
    		}
    		doublePaths.add(points);
    	}
    	return pointsToImage(doublePaths,size);
    }

    public static BufferedImage pointsToImage(List<List<double[]>>paths,int size) {
    	BufferedImage img = new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);
        
    	int[] colorCodes = {
    		    0xFFFF0000,   // Red with full opacity
    		    0xFF00FF00,   // Green with full opacity
    		    0xFF0000FF,   // Blue with full opacity
    		    0xFF00FFFF,   // Cyan with full opacity
    		    0xFFFF00FF,   // Magenta with full opacity
    		    0xFFFFFF00,   // Yellow with full opacity
    		    0xFF000000,   // Black with full opacity
    		    0xFFFFFFFF,   // White with full opacity
    		    0xFF808080,   // Gray with full opacity
    		    0xFFA52A2A,   // Brown with full opacity
    		    0xFFFFA500,   // Orange with full opacity
    		    0xFF800080,   // Purple with full opacity
    		    0xFFFFC0CB,   // Pink with full opacity
    		    0xFF008080,   // Teal with full opacity
    		    0xFF000080,   // Navy with full opacity
    		    0xFF808000    // Olive with full opacity
    		};

    	int a=0;
    	for(int i=0; i<paths.size(); i++) {
    		for(double[] point : paths.get(i)) {
    			try {
    				img.setRGB((int)point[0],(int)point[1], colorCodes[a]);	    				    				
    			}catch(Exception e) {
    				System.out.println("what "+point[0]+" "+point[1]);
    			}
    		}
    		a++;
    		if(a>=colorCodes.length)
    			a = 0;
    	}
		return img;
    }




}
