package com.tiger.CharacterPalace.Service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.tiger.CharacterPalace.util.JsonMapper;

public class KdTreeTest {
    private KdTree kdTree;

    @BeforeEach
    public void setUp() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{0.4, 0.9});
        points.add(new double[]{0.2, 1.0});
        points.add(new double[]{0.1, -0.2});
        points.add(new double[]{0.4, 2.0});
        points.add(new double[]{0.0, -4.0});
        points.add(new double[]{0.6, 0.6});
        points.add(new double[]{0.4, 0.3});
        points.add(new double[]{0.4, 0.4});
        points.add(new double[]{0.0, -1.0});
        points.add(new double[]{10, 10});
        points.add(new double[]{20, 20});
        points.add(new double[]{40, 40});
        points.add(new double[]{80, 80});
        points.add(new double[]{160, 160});
        points.add(new double[]{50, 50});
        points.add(new double[]{100, 100});
        points.add(new double[]{60, 60});
        points.add(new double[]{120, 120});
        kdTree = new KdTree(points);
    }

    @Test    
    public void nearestNeighbor_ReturnsNearestPoint() {
        double[] qp1= new double[]{0.3, 0.3};
        double[] ep1 = new double[]{0.4, 0.3};
        double[] qp2= new double[]{0.4, 0.1};
        double[] ep2 = new double[]{0.4, 0.3};
        double[] qp3= new double[]{1.1, 2.1};
        double[] ep3 = new double[]{0.4, 2.0};
        double[] qp4= new double[]{0.2, 0.3};
        double[] ep4 = new double[]{0.4, 0.3};
        double[] qp5= new double[]{0.6, 0.7};
        double[] ep5 = new double[]{0.6, 0.6};
        double[] qp6= new double[]{95, 95};
        double[] ep6 = new double[]{100, 100};

        Assertions.assertArrayEquals(ep1, kdTree.nearestNeighbor(qp1), 0.0001);
        Assertions.assertArrayEquals(ep2, kdTree.nearestNeighbor(qp2), 0.0001);
        Assertions.assertArrayEquals(ep3, kdTree.nearestNeighbor(qp3), 0.0001);
        Assertions.assertArrayEquals(ep4, kdTree.nearestNeighbor(qp4), 0.0001);
        Assertions.assertArrayEquals(ep5, kdTree.nearestNeighbor(qp5), 0.0001);
        Assertions.assertArrayEquals(ep6, kdTree.nearestNeighbor(qp6), 0.0001);
    }
    


    @Test
    public void nearestNeighbor_ReturnsNearestPoint_WhenTreeIsEmpty() {
        KdTree emptyTree = new KdTree(new ArrayList<>());
        double[] qp = new double[]{0.4, 0.4};
        double[] expected = null;

        Assertions.assertArrayEquals(expected, emptyTree.nearestNeighbor(qp), 0.0001);
    }

    @Test
    public void testJsonVersion() {
    	Gson gson = new Gson();
    	String json = JsonMapper.objectToJson(kdTree.getRoot());
    	System.out.println(json);
    }
}


