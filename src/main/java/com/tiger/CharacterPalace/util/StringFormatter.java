package com.tiger.CharacterPalace.util;

public class StringFormatter {
	public static String[] hanziDefinitionToWords(String string) {
		return string.split("[,;]\\s+");
	}
	public static String getAsOneWord(String string) {
		return string.replaceAll("(?i)\\b(to|an|a)\\b", "").trim();
	}
}

