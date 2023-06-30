	package com.tiger.CharacterPalace.Service;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiger.CharacterPalace.Entity.CharacterEntity;
import com.tiger.CharacterPalace.Repository.CharacterRepository;
import com.tiger.CharacterPalace.util.CharacterUtil;
import com.tiger.CharacterPalace.util.Constants;
import com.tiger.CharacterPalace.util.ImageUtils;
import com.tiger.CharacterPalace.util.JsonMapper;
import com.tiger.CharacterPalace.util.PathCreator;

@Service
public class CharacterServiceImpl implements CharacterService{

	@Autowired
	CharacterRepository repository;
	
	@Override
	public void saveCharacters(Font font) {
		List<CharacterEntity>entities = TtfMapper.createCharacterEntities(font);
		repository.saveAll(entities);
	}

	@Override
	public String getCharacterByUnicode(int unicode) {
		CharacterEntity entity =  repository.findByUnicode(unicode);
		return JsonMapper.entityToJson(entity);
	}

	@Override
	public String getClosestMatch(int unicode) {
		CharacterEntity entity = repository.findByUnicode(unicode);
		CharacterEntity closestMatch = Hausdorff.findClosestMatch(entity);
		return JsonMapper.entityToJson(closestMatch);
	}

	@Override
	public List<CharacterEntity> getAll() {
		return repository.findAll();
	}

	@Override
	public String getParts(int unicode) {
		CharacterEntity entity = CharacterUtil.getEntityMap().get(unicode);
		BufferedImage image = ImageUtils.fontToImages(unicode, CharacterUtil.getFont(), Constants.IMAGE_SIZE);
		List<List<double[]>>paths = PathCreator.imageToPaths(image);
		List<CharacterEntity>result = new ArrayList<>();
		PathCreator.filterPathsByRatio(paths,Constants.FILTER_RATIO);
		
		for(List<double[]>path : paths) {
			PathCreator.normalizePath(path, Constants.IMAGE_SIZE);
			KdTree kdTree = new KdTree(path);
			CharacterEntity closestMatch = Hausdorff.findClosestMatch(kdTree);
			result.add(closestMatch);
		}
		return JsonMapper.objectToJson(result);
	}
	
}
