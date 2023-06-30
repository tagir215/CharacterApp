package com.tiger.CharacterPalace.Service;

import java.awt.Font;

import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.util.FileManager;

class CharacterServiceImplTest {

	@Test
	void test() {
		CharacterServiceImpl service = new CharacterServiceImpl();
		String path = FileManager.getResourcePath("HanyiSentyRubber.ttf");
		Font font = FileManager.createFont(path);
		service.saveCharacters(font);
	}

}
