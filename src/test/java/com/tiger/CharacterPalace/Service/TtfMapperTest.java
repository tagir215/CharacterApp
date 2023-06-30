package com.tiger.CharacterPalace.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Font;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.util.FileManager;

class TtfMapperTest {

	@Test
	void testCreateEntities() {
		String path = FileManager.getResourcePath("HanyiSentyRubber.ttf");
		Font font = FileManager.createFont(path);
		List<CharacterEntity>entities = TtfMapper.createCharacterEntities(font);
		int expectedUnicode = 9520;
		String expectedGraphicData = "wow";
		assertEquals(expectedUnicode,entities.get(363).getUnicode());
		assertEquals(expectedGraphicData,entities.get(363).getGraphicData());
	}

}
