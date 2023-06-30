package com.tiger.CharacterPalace;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.Service.CharacterService;
import com.tiger.CharacterPalace.util.CharacterUtil;
import com.tiger.CharacterPalace.util.FileManager;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class CharacterPalaceApplication {
	
	@Autowired
	CharacterService service;

	public static void main(String[] args) {
		SpringApplication.run(CharacterPalaceApplication.class, args);
	}	
	
	@PostConstruct
	void init() {
		String fontPath = FileManager.getResourcePath("DroidSansFallback.ttf");
		Font font = FileManager.createFont(fontPath);
		//service.saveCharacters(font);
		List<CharacterEntity>entities = service.getAll();
		CharacterUtil.setFont(font);
		CharacterUtil.setEntityMap(entities);
		CharacterUtil.setKdTreeMap(entities);
		System.out.println("maps done");
	}


}
