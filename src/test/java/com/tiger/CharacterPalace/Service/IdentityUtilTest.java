package com.tiger.CharacterPalace.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.service.character.IdentityUtil;

public class IdentityUtilTest {
    
    @Test
    public void testCompareIdentities_WithEqualIdentities_Returns1() {
        List<double[]> id1 = new ArrayList<>();
        id1.add(new double[]{1.0, 2.0, 3.0});
        id1.add(new double[]{4.0, 5.0, 6.0});
        id1.add(new double[]{7.0, 8.0, 9.0});
        
        List<double[]> id2 = new ArrayList<>();
        id2.add(new double[]{1.0, 2.0, 3.0});
        id2.add(new double[]{4.0, 5.0, 6.0});
        id2.add(new double[]{7.0, 8.0, 9.0});
        
        double similarity = IdentityUtil.compareIdentities(id1, id2);
        
        assertEquals(1.0, similarity);
    }
    
    @Test
    public void testCompareIdentities_WithMorePartialSimilarity_ReturnsPartialSimilarity() {
        List<double[]> id1 = new ArrayList<>();
        id1.add(new double[]{1.0, 2.0, 3.0});
        id1.add(new double[]{4.0, 5.0, 6.0});
        id1.add(new double[]{7.0, 8.0, 9.0});
        
        List<double[]> id2 = new ArrayList<>();
        id2.add(new double[]{4.0, 5.0, 6.0});
        id2.add(new double[]{7.0, 8.0, 9.0});
        
        double similarity = IdentityUtil.compareIdentities(id1, id2);
        
        assertEquals(0.6, similarity,0.1);
    }
    
    @Test
    public void testCompareIdentities_WithLessPartialSimilarity_ReturnsPartialSimilarity() {
        List<double[]> id1 = new ArrayList<>();
        id1.add(new double[]{1.0, 2.0, 3.0});
        id1.add(new double[]{4.0, 5.0, 6.0});
        id1.add(new double[]{7.0, 8.0, 9.0});
        
        List<double[]> id2 = new ArrayList<>();
        id2.add(new double[]{4.0, 5.0, 7.0});
        id2.add(new double[]{7.0, 8.0, 9.0});
        
        double similarity = IdentityUtil.compareIdentities(id1, id2);
        
        assertEquals(0.3, similarity,0.1);
    }
    
    @Test
    public void testCompareIdentities_WithNoSimilarity_Returns0() {
        List<double[]> id1 = new ArrayList<>();
        id1.add(new double[]{1.0, 2.0, 3.0});
        id1.add(new double[]{4.0, 5.0, 6.0});
        
        List<double[]> id2 = new ArrayList<>();
        id2.add(new double[]{7.0, 8.0, 9.0});
        id2.add(new double[]{10.0, 11.0, 12.0});
        
        double similarity = IdentityUtil.compareIdentities(id1, id2);
        
        assertEquals(0.0, similarity);
    }
}

