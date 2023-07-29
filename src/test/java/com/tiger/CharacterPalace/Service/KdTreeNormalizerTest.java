package com.tiger.CharacterPalace.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.KdTree;
import com.tiger.CharacterPalace.service.character.KdTreeNormalizer;

public class KdTreeNormalizerTest {

    @Test
    public void testNormalizeTrees() {
        // Create the control tree
        List<double[]> controlPoints = new ArrayList<>();
        controlPoints.add(new double[]{0, 0});
        controlPoints.add(new double[]{2, 2});
        controlPoints.add(new double[]{4, 4});
        KdTree controlTree = new KdTree(controlPoints);
        controlTree.calculateDimensions();

        // Create the transforming tree
        List<double[]> transformingPoints = new ArrayList<>();
        transformingPoints.add(new double[]{1, 1});
        transformingPoints.add(new double[]{3, 3});
        KdTree transformingTree = new KdTree(transformingPoints);
        transformingTree.calculateDimensions();
        // Normalize the trees
        KdTreeNormalizer.normalizeTrees(controlTree, transformingTree);



        // Check the dimensions of the transforming tree
        Assertions.assertEquals(4.0, transformingTree.getWidth(), 0);
        Assertions.assertEquals(4.0, transformingTree.getHeight(), 0);
        Assertions.assertEquals(0, transformingTree.getLeftX());
        Assertions.assertEquals(4, transformingTree.getRightX());
        Assertions.assertEquals(0, transformingTree.getTopY());
        Assertions.assertEquals(4, transformingTree.getBottomY());
    }
}


