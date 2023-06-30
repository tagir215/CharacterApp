package com.tiger.CharacterPalace.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageUtilsTest {
	Font font;
	@BeforeEach
	public void setup() {
		String path = FileManager.getResourcePath("XiaolaiMonoSC-Regular.ttf");
		font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testImage() throws InterruptedException {
		
		BufferedImage image = ImageUtils.fontToImages(34910,font,200f);
		//ImageUtils.displayImage(image,3);
		//Thread.sleep(2000);
		 
	}
	
	
}
