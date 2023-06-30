package com.tiger.CharacterPalace.Service;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.util.FileManager;
import com.tiger.CharacterPalace.util.ImageUtils;
import com.tiger.CharacterPalace.util.JsonMapper;
import com.tiger.CharacterPalace.util.PathCreator;
import com.tiger.CharacterPalace.util.ZhangSuen;

public class TtfMapper {
	private static int count = 0;
	private static final int FILTER_RATIO = 20;
	private static final int IMAGE_SIZE = 200;
	
	public static List<CharacterEntity>createCharacterEntities(Font font){
		System.out.println("creating list");
		
		List<MyShape>shapes = ttfToCurves(font);
		System.out.println("crating unicodeTrie");
		UnicodeTrie trie = new UnicodeTrie();
		System.out.println("done\ncreating hanzies");
		Hanzi[] hanzies = JsonMapper.jsonToHanzi(FileManager.fileToString("hanziDB.json"));
		for(Hanzi hanzi : hanzies) {
			trie.insert(hanzi);
		}
		System.out.println("done");
		return curvesToEntities(shapes,trie,font);
	}
	
    public static List<MyShape> ttfToCurves(Font font) {
    	List<MyShape>shapes = new ArrayList<>();
           
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); 
        Graphics2D g2d = image.createGraphics();
        FontRenderContext frc = g2d.getFontRenderContext();

        for (int codePoint = 0; codePoint <= Character.MAX_CODE_POINT; codePoint++) {
        
            if (Character.isValidCodePoint(codePoint) && font.canDisplay(codePoint)) {
            	MyShape shape = new MyShape(codePoint);
            	char[] chars = Character.toChars(codePoint);
                String character = new String(chars);
                GlyphVector glyphVector = font.createGlyphVector(frc, character); 
                
                for (int i = 0; i < glyphVector.getNumGlyphs(); i++) {
          
                    PathIterator pathIterator = glyphVector.getGlyphOutline(i).getPathIterator(null);
                    double[] coords = new double[6];
                     
                    while (!pathIterator.isDone()) {
                    	
                    	Path curve = new Path();
                        int type = pathIterator.currentSegment(coords);
                        
                        
                        switch (type) {
                            case PathIterator.SEG_MOVETO:
                            	curve.setType("MOVETO");
                            	curve.addCoordinate(new double[] {coords[0],coords[1]});
                                break;
                            case PathIterator.SEG_LINETO:
                            	curve.setType("LINETO");
                            	curve.addCoordinate(new double[] {coords[0],coords[1]});
                                break;
                            case PathIterator.SEG_QUADTO:
                            	curve.setType("QUADTO");
                            	curve.addCoordinate(new double[] {coords[0],coords[1]});
                            	curve.addCoordinate(new double[] {coords[2],coords[3]});
                                break;
                            case PathIterator.SEG_CUBICTO:
                            	curve.setType("CUBICTO");
                            	curve.addCoordinate(new double[] {coords[0],coords[1]});
                            	curve.addCoordinate(new double[] {coords[2],coords[3]});
                            	curve.addCoordinate(new double[] {coords[4],coords[5]});	
                                break;
                            case PathIterator.SEG_CLOSE:
                            	curve.setType("CLOSE");
                                break;
                        }
                                                    
                        shape.addCurve(curve);
                        pathIterator.next();
                    }

                }
                shapes.add(shape);
                
            }

        }

        
		return shapes;
    }
    
    
    public static List<CharacterEntity> curvesToEntities(List<MyShape>shapes,UnicodeTrie trie,Font font) {
		List<CharacterEntity>entities = new ArrayList<>();
		System.out.println("creating lmtrie");
		ColorSelector cSelector = new ColorSelector(FileManager.fileToTrie("glove.6B.100d.txt"));
		System.out.println("done\nshapes to entities "+shapes.size());
		for(MyShape shape : shapes) {
			CharacterEntity entity = new CharacterEntity();
			entity.setUnicode(shape.getUnicode());
			entity.setGraphicData(JsonMapper.curvesToJson(shape.getCurves()));
			Hanzi hanzi = trie.search(shape.getUnicode());
			if(hanzi!=null) {
				entity.setFrequency_rank(hanzi.getFrequency_rank());
				entity.setHsk_levl(hanzi.getHsk_levl());
				entity.setPinyin(hanzi.getPinyin());
				entity.setStroke_count(hanzi.getStroke_count());
				entity.setDefinition(hanzi.getDefinition());	
				String[] definitions = entity.getDefinition().split("[,;]")[0].split(" ");
				String color = cSelector.selectColor(definitions);
				if(color!=null) {
					entity.setColor(color);
				}else {
					entity.setColor("gray");
				}
				KdTree kdtree = createKdTree(entity,font);
				entity.setIdentityData(JsonMapper.objectToJson(kdtree.getRoot()));
			}
			else {
				continue;
			}
			if(count%100==0) {
				System.out.println(" :? "+count);
			}
			System.out.print("-");
			count++;
			entities.add(entity);
		}
    	return entities;
    	
    }
    
    public static KdTree createKdTree(CharacterEntity entity,Font font) {
    	BufferedImage imageVersion = ImageUtils.fontToImages(entity.getUnicode(), font, IMAGE_SIZE);
		ZhangSuen.skeletonize(imageVersion);
		List<List<double[]>>paths = PathCreator.imageToPaths(imageVersion);
		PathCreator.filterPathsByRatio(paths,FILTER_RATIO);
		List<double[]>points = new ArrayList<>();
		for(List<double[]>path : paths) {
			for(double[] point : path) {
				points.add(point);
			}
		}
 		return new KdTree(points);
    }
    	
    
}
