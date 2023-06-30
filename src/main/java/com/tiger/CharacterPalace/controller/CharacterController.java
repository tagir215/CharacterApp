package com.tiger.CharacterPalace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tiger.CharacterPalace.Service.CharacterService;

@RestController
public class CharacterController {
	
	@Autowired
	CharacterService service;
	
	@GetMapping("api/v1/getcoords/{unicode}")
	public String getCoordinates(@PathVariable int unicode) {
		return service.getCharacterByUnicode(unicode);
	}
	
	@GetMapping("api/v1/getmatch/{unicode}")
	public String getClosestMatch(@PathVariable int unicode) {
		return service.getClosestMatch(unicode);
	}
	
	@GetMapping("api/v1/getparts/{unicode}")
	public String getParts(@PathVariable int unicode) {
		return service.getParts(unicode);
	}
}
