package com.tiger.CharacterPalace.service.connection;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tiger.CharacterPalace.entity.DefinitionEntity;
import com.tiger.CharacterPalace.entity.HanziEntity;
import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.LmTrie;
import com.tiger.CharacterPalace.repository.HanziRepository;
import com.tiger.CharacterPalace.service.character.HanziEntityMapper;
import com.tiger.CharacterPalace.util.JsonMapper;
import com.tiger.CharacterPalace.util.MyLinearAlgebra;



@Service
public class ConnectionService {
	private HanziRepository repository;

	public ConnectionService(HanziRepository repository) {
		super();
		this.repository = repository;
	}

	public String similarity(int unicode1, int unicode2) {
		HanziEntity entity1 = repository.findByUnicode(unicode1).get(0);
		HanziEntity entity2 = repository.findByUnicode(unicode2).get(0);
		String bestMatchString = "";
		double bestMatchValue = 0;
		for(DefinitionEntity defEntity : entity1.getDefinitions()) {
			double[] embs1 = JsonMapper.jsonToDoubleArray(defEntity.getEmbeddings());
			if(embs1==null)
				continue;
			for(DefinitionEntity defEntity2 : entity2.getDefinitions()) {
				double[] embs2 = JsonMapper.jsonToDoubleArray(defEntity2.getEmbeddings());
				if(embs2==null)
					continue;
				double sim = MyLinearAlgebra.cosineSimilarity(embs2, embs1);
				if(sim>bestMatchValue) {
					bestMatchValue = sim;
					bestMatchString = defEntity.getDefinition()+" <-> "+defEntity2.getDefinition();
				}
			}
		}
		Match m = new Match(bestMatchString,bestMatchValue);
		String charsman = new String(Character.toChars(unicode1))+ " "+ new String(Character.toChars(unicode2));
		m.unicodes = charsman;
		return JsonMapper.objectToJson(m);
	}
	
	private class Match{
		String string;
		double value;
		String unicodes = "";
		public Match(String string, Double value) {
			this.string = string;
			this.value = value;
		}
	}
	public void saveHanzies(LmTrie lmTrie, Hanzi[] hanzies) {
		HanziEntityMapper mapper = new HanziEntityMapper();
		List<HanziEntity>entities = mapper.createHanziEntites(lmTrie, hanzies);
		repository.saveAll(entities);
	}
	
	public String getDecks(List<Integer> unicodes) {
		List<HanziEntity>entities = repository.findByUnicodes(unicodes);
		String json = JsonMapper.hanziEntitiesToJson(entities);
		return json;
	}
}
