package com.tiger.CharacterPalace.Service;

import java.util.HashMap;

public class LmTrie {
	Node root = new Node('-');
	class Node{
		char c;
		HashMap<Character,Node>children = new HashMap<>();
		double[] embeddings;
		public Node(char c) {
			this.c = c;
		}
	}
	
	public void insert(String line) {
		String[] splitted = line.split(" ");
		String word = splitted[0].toLowerCase();
		double[] embeddings = new double[splitted.length-1];
		for(int i=1; i<splitted.length; i++) {
			embeddings[i-1] = Double.parseDouble(splitted[i]);
		}
		Node node = root;
		for(int i=0; i<word.length(); i++) {
			char c = word.charAt(i);
			if(node.children.get(c)==null) {
				node.children.put(c, new Node(c));
				node = node.children.get(c);
			}else {
				node = node.children.get(c);
			}
		}
		node.embeddings = embeddings;
	}
	
	double[] search(String word) {
		Node node = root;
		for(int i=0; i<word.length(); i++) {
			char c = word.charAt(i);
			node = node.children.get(c);
			if(node == null) {
				return null;
			}
		}
		return node.embeddings;
	}
}
