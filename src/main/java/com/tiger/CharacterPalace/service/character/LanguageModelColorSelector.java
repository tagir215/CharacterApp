package com.tiger.CharacterPalace.service.character;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.tiger.CharacterPalace.model.LmTrie;
import com.tiger.CharacterPalace.util.MyLinearAlgebra;

public class LanguageModelColorSelector {
	private final LmTrie trie;
	private LinkedHashMap<String,double[]>moodColorMap = new LinkedHashMap<>();
	private final String[] words = {
		    // Moods for red
		    "passionate",
		    "bold",
		    "energetic",
		    "powerful",
		    "warm",
		    "fiery",
		    "exciting",
		    "intense",
		    "daring",
		    "dynamic",
		    "brave",
		    "vibrant",
		    
		    // Moods for blue
		    "calm",
		    "serene",
		    "tranquil",
		    "peaceful",
		    "soothing",
		    "gentle",
		    "relaxing",
		    "melancholic",
		    "dreamy",
		    "mysterious",
		    "ethereal",
		    "harmonious",
		    
		    // Moods for green
		    "refreshing",
		    "natural",
		    "harmonious",
		    "rejuvenating",
		    "vital",
		    "serene",
		    "relaxing",
		    "balanced",
		    "hopeful",
		    "peaceful",
		    "revitalizing",
		    "uplifting",
		    
		    // Moods for yellow
		    "joyful",
		    "optimistic",
		    "cheerful",
		    "sunny",
		    "bright",
		    "lively",
		    "energetic",
		    "playful",
		    "happy",
		    "radiant",
		    "vibrant",
		    "upbeat",
		    
		    // Moods for orange
		    "warm",
		    "invigorating",
		    "enthusiastic",
		    "creative",
		    "vibrant",
		    "exciting",
		    "playful",
		    "energetic",
		    "passionate",
		    "bold",
		    "dynamic",
		    "warmhearted",
		    
		    // Moods for purple
		    "royal",
		    "mystical",
		    "creative",
		    "imaginative",
		    "spiritual",
		    "magical",
		    "inspiring",
		    "mysterious",
		    "elegant",
		    "enchanting",
		    "soothing",
		    "romantic",
		    
		    // Moods for pink
		    "sweet",
		    "romantic",
		    "tender",
		    "playful",
		    "gentle",
		    "feminine",
		    "delicate",
		    "affectionate",
		    "compassionate",
		    "innocent",
		    "loving",
		    "charming",
		    
		    // Moods for brown
		    "earthy",
		    "warm",
		    "comforting",
		    "rustic",
		    "natural",
		    "cozy",
		    "reliable",
		    "grounded",
		    "simple",
		    "wholesome",
		    "sturdy",
		    "earthy",
		    
		    // Moods for black
		    "mysterious",
		    "powerful",
		    "elegant",
		    "sophisticated",
		    "edgy",
		    "chic",
		    "bold",
		    "classic",
		    "dramatic",
		    "rebellious",
		    "luxurious",
		    "sleek",
		    
		    // Moods for white
		    "pure",
		    "peaceful",
		    "serene",
		    "clean",
		    "simple",
		    "minimalistic",
		    "fresh",
		    "bright",
		    "innocent",
		    "harmonious",
		    "tranquil",
		    "light"
		};


	private final String[] colors = {
		    "red",
		    "blue",
		    "green",
		    "yellow",
		    "orange",
		    "purple",
		    "pink",
		    "brown",
		    "black",
		    "white"
	};


	public LanguageModelColorSelector(LmTrie trie) {
		this.trie = trie;
		final int size = 12;
		int j=0;
		for(int i=0; i<words.length; i++) {
			if((i+1)%size==0) {
				List<double[]>embs = new ArrayList<>();
				for(int a=0; a<size; a++) {
					embs.add(trie.search(words[i-a]));
				}
				double[] avgEmb = MyLinearAlgebra.averageCosineSimilarity(embs);
				moodColorMap.put(colors[j], avgEmb);
				j++;
			}
 		}
	}
	
	public String selectColor(String[] words) {
		List<double[]>embeddings = new ArrayList<>();
		for(String s : words) {
			double[]embs = trie.search(s);
			if(embs!=null) {
				embeddings.add(embs);				
			}
		}
		double[] wordEmbedding = MyLinearAlgebra.averageCosineSimilarity(embeddings);
		if(wordEmbedding==null) {
			return null;
		}
		double mostCosineSim=-1;
		String result = "";
		for(String color : moodColorMap.keySet()) {
			double cosineSim = MyLinearAlgebra.cosineSimilarity(moodColorMap.get(color), wordEmbedding);
			if(cosineSim>mostCosineSim) {
				mostCosineSim = cosineSim;
				result = color;
			}
		}
		return result;
	}
	

}
