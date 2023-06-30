package com.tiger.CharacterPalace.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MySortTest {
    @Test
    public void testMergeSortX() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{5.2, 3.1});
        points.add(new double[]{1.5, 2.7});
        points.add(new double[]{3.8, 4.2});
        points.add(new double[]{2.1, 6.5});
        
        MySort.mergeSort(points, 0); 
        
        List<double[]> expected = new ArrayList<>();
        expected.add(new double[]{1.5, 2.7});
        expected.add(new double[]{2.1, 6.5});
        expected.add(new double[]{3.8, 4.2});
        expected.add(new double[]{5.2, 3.1});
        
        assertEquals(expected.get(0)[0], points.get(0)[0]);
        assertEquals(expected.get(1)[0], points.get(1)[0]);
        assertEquals(expected.get(2)[0], points.get(2)[0]);
        assertEquals(expected.get(3)[0], points.get(3)[0]);
    }
    
    @Test
    public void testMergeSortY() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{5.2, 3.1});
        points.add(new double[]{1.5, 2.7});
        points.add(new double[]{3.8, 4.2});
        points.add(new double[]{2.1, 6.5});
        
        MySort.mergeSort(points, 1);
        
        List<double[]> expected = new ArrayList<>();
        expected.add(new double[]{1.5, 2.7});
        expected.add(new double[]{5.2, 3.1});
        expected.add(new double[]{3.8, 4.2});
        expected.add(new double[]{2.1, 6.5});
        
        assertEquals(expected.get(0)[0], points.get(0)[0]);
        assertEquals(expected.get(1)[0], points.get(1)[0]);
        assertEquals(expected.get(2)[0], points.get(2)[0]);
        assertEquals(expected.get(3)[0], points.get(3)[0]);
    }
}
