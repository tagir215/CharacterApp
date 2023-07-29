package com.tiger.CharacterPalace.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tiger.CharacterPalace.service.character.CharacterService;
import com.tiger.CharacterPalace.service.connection.ConnectionService;

@RestController
public class CharacterController {
	
	private final CharacterService characterService;
	private final ConnectionService connectionService;

    public CharacterController(CharacterService characterService, ConnectionService connectionService) {
        this.characterService = characterService;
        this.connectionService = connectionService;
    }
	
	@GetMapping("api/v1/getcoords/{unicode}")
	public String getCoordinates(@PathVariable int unicode) {
		return characterService.getCharacterByUnicode(unicode);
	}
	
	@GetMapping("api/v1/getmatch/{unicode}")
	public String getClosestMatch(@PathVariable int unicode) {
		return characterService.getClosestMatch(unicode);
	}
	
	@GetMapping("api/v1/getparts/{unicode}")
	public String getParts(@PathVariable int unicode) {
		return characterService.getParts(unicode);
	}
	
	@GetMapping(value="api/v1/get_similarity/{unicode1}/{unicode2}", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
	public String getSimilarity(@PathVariable int unicode1, @PathVariable int unicode2) {
		return connectionService.similarity(unicode1, unicode2);
	}
	
	@GetMapping(value="api/v1/get_decks/{unicodes}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	public String getDecks(@PathVariable List<Integer>unicodes){
		return connectionService.getDecks(unicodes);
	}
}
