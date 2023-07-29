package com.tiger.CharacterPalace.service.character;

import java.util.ArrayList;
import java.util.List;

import com.tiger.CharacterPalace.entity.DefinitionEntity;
import com.tiger.CharacterPalace.entity.HanziEntity;
import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.LmTrie;
import com.tiger.CharacterPalace.util.JsonMapper;
import com.tiger.CharacterPalace.util.StringFormatter;

public class HanziEntityMapper {
	
	
	
	public List<HanziEntity> createHanziEntites(LmTrie lmTrie, Hanzi[] hanzies){
		LanguageModelColorSelector colorSelector = new LanguageModelColorSelector(lmTrie);
		List<HanziEntity>entities = new ArrayList<>();
		for(Hanzi hanzi : hanzies) {
			HanziEntity entity = new HanziEntity();
			entity.setFrequency_rank(hanzi.getFrequency_rank());
			entity.setHsk_levl(hanzi.getHsk_levl());
			entity.setPinyin(hanzi.getPinyin());
			entity.setRadical(hanzi.getRadical());
			entity.setRadical_code(hanzi.getRadical_code());
			entity.setStroke_count(hanzi.getStroke_count());
	        int codePoint = Character.codePointAt(hanzi.getCharcter(), 0);
			entity.setUnicode(codePoint);
			
			String[] definitions = StringFormatter.hanziDefinitionToWords(hanzi.getDefinition());
			String color = colorSelector.selectColor(definitions);
			if(color!=null) {
				entity.setColor(color);
			}
			
			List<DefinitionEntity>definitionEntities = new ArrayList<>();
			for(String def : definitions) {
				DefinitionEntity defEntity = new DefinitionEntity();
				defEntity.setHanziEntity(entity);
				defEntity.setDefinition(def);
				if(def.contains(" ")) {
					def = StringFormatter.getAsOneWord(def);
				}
				double embeddings[] = lmTrie.search(def);
				defEntity.setEmbeddings(JsonMapper.objectToJson(embeddings));
				definitionEntities.add(defEntity);
			}
			entity.setDefinitions(definitionEntities);
			entities.add(entity);

		}
		return entities;
	}
	
	public static Hanzi entityToHanzi(HanziEntity entity) {
		Hanzi hanzi = new Hanzi();
		char[] chars = Character.toChars(entity.getUnicode());
		String character = new String(chars);
		
		hanzi.setCharcter(character);
		hanzi.setFrequency_rank(entity.getFrequency_rank());
		hanzi.setHsk_levl(entity.getHsk_levl());
		hanzi.setPinyin(entity.getPinyin());
		hanzi.setStroke_count(entity.getStroke_count());
		hanzi.setRadical(entity.getRadical());
		hanzi.setColor(entity.getColor());
		List<DefinitionEntity>defs = entity.getDefinitions();
		StringBuilder stringBuilder = new StringBuilder();
		for(DefinitionEntity e : defs) {
			stringBuilder.append(e.getDefinition()+"; ");
		}
		hanzi.setDefinition(stringBuilder.toString());
		return hanzi;
	}
}
