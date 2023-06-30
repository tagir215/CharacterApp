package com.tiger.CharacterPalace.util;

import java.util.List;

public class MyLinearAlgebra {
    public static double cosineSimilarity(double[] embedding1, double[] embedding2) {
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        for (int i = 0; i < embedding1.length; i++) {
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += embedding1[i] * embedding1[i];
            norm2 += embedding2[i] * embedding2[i];
        }

        double magnitude1 = Math.sqrt(norm1);
        double magnitude2 = Math.sqrt(norm2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0;
        } else {
            double similarity = dotProduct / (magnitude1 * magnitude2);
            return similarity;
        }
    }

    public static double[] averageCosineSimilarity(List<double[]> embeddings) {
        final int numEmbeddings = embeddings.size();
        if (numEmbeddings < 1) {
            return null;
        }
        final int size = embeddings.get(0).length;
        double[] avgEmb = new double[size];
        
        for (int i = 0; i < size; i++) {
        	double sum = 0;
        	for(int j=0; j<numEmbeddings; j++) {
        		double e = embeddings.get(j)[i];
        		sum+=e;
        	}
        	avgEmb[i] = sum/numEmbeddings;
        }
        return avgEmb;
    }
}

