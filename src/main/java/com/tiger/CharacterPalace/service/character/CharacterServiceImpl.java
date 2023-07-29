	package com.tiger.CharacterPalace.service.character;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tiger.CharacterPalace.entity.CharacterEntity;
import com.tiger.CharacterPalace.model.KdTree;
import com.tiger.CharacterPalace.model.PathPoint;
import com.tiger.CharacterPalace.repository.CharacterRepository;
import com.tiger.CharacterPalace.util.Constants;
import com.tiger.CharacterPalace.util.ImageUtils;
import com.tiger.CharacterPalace.util.JsonMapper;

@Service
public class CharacterServiceImpl implements CharacterService{

	CharacterRepository repository;
		
	public CharacterServiceImpl(CharacterRepository repository) {
		this.repository = repository;
	}

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
		return null;
	}

	@Override
	public List<CharacterEntity> getAll() {
		return repository.findAll();
	}

	@Override
	public String getParts(int unicode) {
		System.out.println("looking for "+unicode);
		CharacterEntity entity = CharacterUtil.getEntityMap().get(unicode);
		BufferedImage image = ImageUtils.fontToImages(unicode, CharacterUtil.getFont(), Constants.IMAGE_SIZE);
		List<List<PathPoint>>paths = PathCreator.imageToPaths(image);
		IdentityCreator iCreator = new IdentityCreatorI();
		List<List<double[]>>filteredPaths = iCreator.pathsIdentityPoints(paths);
		List<KdTree>kdTrees = new ArrayList<>();
		for(List<double[]>path : filteredPaths) {
			KdTree kdTree = new KdTree(path);
			kdTrees.add(kdTree);
		}
		List<CharacterEntity>result = new ArrayList<>();
		populate1(result,kdTrees);
		System.out.println("result lenght "+result.size());
		return JsonMapper.objectToJson(result);
	}
	

	
	private static void populate1(List<CharacterEntity>result,List<KdTree>kdTrees) {
		double smallestDist = Double.MAX_VALUE;
		CharacterEntity bestMatch = null;
		for(KdTree kdTree1 : kdTrees) {	
			for(Integer unicode : CharacterUtil.getKdTreeMap().keySet()) {
				KdTree kdTree2 = CharacterUtil.getKdTreeMap().get(unicode);
				KdTreeNormalizer.normalizeTrees(kdTree1, kdTree2);
				double dist = Hausdorff.getHausdorffsDistance(kdTree1, kdTree2);
				if(dist<smallestDist) {
					smallestDist = dist;
					bestMatch= CharacterUtil.getEntityMap().get(unicode);
				}
			}
		}
		result.add(bestMatch);
	}

	
	
	
}
