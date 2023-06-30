package com.tiger.CharacterPalace.Service;

import java.awt.Font;
import java.util.List;

import com.tiger.CharacterPalace.Entity.CharacterEntity;

public interface CharacterService {
	public void saveCharacters(Font font);
	public String getCharacterByUnicode(int unicode);
	public String getClosestMatch(int unicode);
	public List<CharacterEntity>getAll();
	public String getParts(int unicode);
}
