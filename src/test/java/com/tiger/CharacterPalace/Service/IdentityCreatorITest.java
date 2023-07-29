package com.tiger.CharacterPalace.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.PathPoint;
import com.tiger.CharacterPalace.service.character.IdentityCreatorI;

class IdentityCreatorITest {
	 
    @Test
    void createDirectionList_shouldReturnExpectedDirections() {
        // Prepare input
        List<PathPoint> path = new ArrayList<>();
        int size = 30;
        for(int i=0; i<size; i++) {
        	path.add(new PathPoint(new double[] {i,0}));
        }
        for(int i=0; i<size; i++) {
        	path.add(new PathPoint( new double[] {10,i}));
        }
        for(int i=size; i>=0; i--) {
        	path.add(new PathPoint(new double[] {i,10}));
        }
        for(int i=size; i>=0; i--) {
        	path.add(new PathPoint(new double[] {0,i}));
        }
        
        IdentityCreatorI creator = new IdentityCreatorI();
        List<double[]>directions =  creator.createDirectionList(path);
        
        for(double[] dir : directions) {
        	System.out.println(dir[0]+" "+dir[1]);
        }
     
    }

}
