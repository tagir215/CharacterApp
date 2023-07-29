package com.tiger.CharacterPalace.service.character;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.tiger.CharacterPalace.entity.CharacterEntity;
import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.KdTree;
import com.tiger.CharacterPalace.model.MyShape;
import com.tiger.CharacterPalace.model.Path;
import com.tiger.CharacterPalace.model.PathPoint;
import com.tiger.CharacterPalace.model.UnicodeTrie;
import com.tiger.CharacterPalace.util.Constants;
import com.tiger.CharacterPalace.util.FileManager;
import com.tiger.CharacterPalace.util.ImageUtils;
import com.tiger.CharacterPalace.util.JsonMapper;
import com.tiger.CharacterPalace.util.ZhangSuen;

public class TtfMapper {
	private static int count = 0;
	
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
		LanguageModelColorSelector cSelector = new LanguageModelColorSelector(FileManager.fileToTrie("glove.6B.100d.txt"));
		System.out.println("done\nshapes to entities "+shapes.size());
		IdentityCreator iCreator = new IdentityCreatorI();
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
				fillEntity(entity,font,iCreator);
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
    
    
    private static void fillEntity(CharacterEntity entity,Font font, IdentityCreator iCreator){
    	BufferedImage image = ImageUtils.fontToImages(entity.getUnicode(), font, Constants.IMAGE_SIZE);
    	ZhangSuen.skeletonize(image);
    	List<List<PathPoint>>paths = PathCreator.imageToPaths(image);
    	List<List<double[]>>identityPoints = iCreator.pathsIdentityPoints(paths);
    	List<double[]>onePath = new ArrayList<>();
    	for(List<double[]>path : identityPoints) {
    		onePath.addAll(path);
    	}
    	KdTree kdTree = new KdTree(onePath);
    	entity.setIdentityData(JsonMapper.objectToJson(kdTree.getRoot()));
    }
    
}
