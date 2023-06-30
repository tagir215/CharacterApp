package com.tiger.CharacterPalace.util;

import java.util.ArrayList;
import java.util.List;

public class MySort {
	
	public static void mergeSort(List<double[]>points,int type) {
		if(points.size()<=1) {
			return;
		}
		
		List<double[]>left = new ArrayList<>();
		List<double[]>right = new ArrayList<>();
		int pivot = points.size()/ 2;
		for(int i=0; i<points.size(); i++) {
			if(i<pivot) {
				left.add(points.get(i));				
			}else {
				right.add(points.get(i));
			}
		}
		mergeSort(left,type);
		mergeSort(right,type);
		
		points.clear();
		int i=0;
		int j=0;
		while(i<left.size() || j<right.size()) {
		
			if(i>=left.size()) {
				points.add(right.get(j));
				j++;
				continue;
			}else if(j>=right.size()) {
				points.add(left.get(i));
				i++;
				continue;
			}
			double l = left.get(i)[type];
			double r = right.get(j)[type];	
			
			if(l<=r) {
				points.add(left.get(i));
				i++;
			}else {
				points.add(right.get(j));
				j++;
			}
			
		}
		
	}
}
