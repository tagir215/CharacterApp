package com.tiger.CharacterPalace.service;

import java.awt.Font;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiger.CharacterPalace.entity.CharacterEntity;
import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.LmTrie;
import com.tiger.CharacterPalace.repository.HanziRepository;
import com.tiger.CharacterPalace.service.character.CharacterService;
import com.tiger.CharacterPalace.service.character.CharacterUtil;
import com.tiger.CharacterPalace.service.connection.ConnectionService;
import com.tiger.CharacterPalace.util.FileManager;
import com.tiger.CharacterPalace.util.JsonMapper;

@Service
public class DataInitService {
	
	@Autowired
	CharacterService characterService;
	@Autowired
	ConnectionService connectionService;
	@Autowired
	HanziRepository hanziRepository;
	
	
	//@PostConstruct
	public void saveEntities() {
		System.out.println("initiating data");
		LmTrie lmTrie = FileManager.fileToTrie("glove.6B.100d.txt");
		Hanzi[] hanzies = JsonMapper.jsonToHanzi(FileManager.fileToString("hanziDB.json"));
		System.out.println("done");
		connectionService.saveHanzies(lmTrie, hanzies);
	}
	
	
	public void init() {
		String fontPath = FileManager.getResourcePath("DroidSansFallback.ttf");
		Font font = FileManager.createFont(fontPath);
		//service.saveCharacters(font);
		List<CharacterEntity>entities = characterService.getAll();
		CharacterUtil.setFont(font);
		CharacterUtil.setEntityMap(entities);
		CharacterUtil.setKdTreeMap(entities);
		System.out.println("maps done");
	}
		
}
