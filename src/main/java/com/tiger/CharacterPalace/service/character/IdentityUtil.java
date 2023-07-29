package com.tiger.CharacterPalace.service.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IdentityUtil {
	private static final double INCREMENT_FULL = 1;
	
	public static double compareIdentities(List<double[]>id1, List<double[]>id2) {	
		List<Double>results = new ArrayList<>();
		int dimension = 0;
		int i=0;
		int j=0;
		getSimilarities(id1,id2,i,j,results,dimension);
		double bestSimilarity = 0;
		for(double sim : results) {
			if(sim>bestSimilarity) {
				bestSimilarity = sim;
			}
		}
		int longerLength = id1.size() > id2.size() ? id1.size() : id2.size();
		return bestSimilarity/longerLength;
	}
	private static void getSimilarities(List<double[]>id1, List<double[]>id2, int i, int j, List<Double>results,int dimension){
		if(i>=id1.size() || j>=id2.size() || dimension>9) {
			return;
		}
		if(dimension>=results.size()) {
			results.add(0.0);
		}
		
		if(Arrays.equals(id1.get(i), id2.get(j))) {
			results.set(dimension, results.get(dimension)+INCREMENT_FULL);
			getSimilarities(id1,id2,i+1,j+1,results,dimension);
		}else {
			getSimilarities(id1,id2,i+1,j+1,results,dimension);
			getSimilarities(id1,id2,i,j+1,results,results.size());
			getSimilarities(id1,id2,i+1,j,results,results.size());
		}
		
	}
}
