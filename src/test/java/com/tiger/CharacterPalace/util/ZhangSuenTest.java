package com.tiger.CharacterPalace.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZhangSuenTest {
	BufferedImage image;

	@BeforeEach
	public void setup() {
		String path = FileManager.getResourcePath("DroidSansFallback.ttf");
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image = ImageUtils.fontToImages(24391,font,200f);
	}
	
	@Test
	void testThinning() throws InterruptedException {
		
		ZhangSuen.skeletonize(image);
		//ImageUtils.displayImage(image,3);
		
		 
	}
}
