package com.tiger.CharacterPalace.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyLinearAlgebraTest {
    @Test
    void testCosineSimilarity() {
        double[] embedding1 = {1.0, 2.0, 3.0};
        double[] embedding2 = {4.0, 5.0, 6.0};
        double expectedSimilarity = 0.9746318461970762;

        double similarity = MyLinearAlgebra.cosineSimilarity(embedding1, embedding2);

        assertEquals(expectedSimilarity, similarity, 0.0001);
    }

    @Test
    void testCosineSimilarityWithZeroVector() {
        double[] embedding1 = {0.0, 0.0, 0.0};
        double[] embedding2 = {1.0, 2.0, 3.0};
        double expectedSimilarity = 0.0;

        double similarity = MyLinearAlgebra.cosineSimilarity(embedding1, embedding2);

        assertEquals(expectedSimilarity, similarity, 0.0001);
    }
    
    @Test
    public void testAverageCosineSimilarity() {
        List<double[]> embeddings = new ArrayList<>();
        embeddings.add(new double[]{0.5, 0.8, 0.2});
        embeddings.add(new double[]{0.7, 0.3, 0.9});
        embeddings.add(new double[]{0.1, 0.6, 0.4});

        double[] averageEmbedding = MyLinearAlgebra.averageCosineSimilarity(embeddings);
        double[] expectedAverageEmbedding = {0.433333333, 0.566666666, 0.5};

        Assertions.assertArrayEquals(expectedAverageEmbedding, averageEmbedding, 0.000001);
    }
}
