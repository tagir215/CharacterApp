package com.tiger.CharacterPalace.model;

public class UnicodeTrie {
	Node root = new Node('-');
	class Node {
		char c;
		Node[] children = new Node[10];
		Hanzi hanzi;
		
		public Node(char c) {
			this.c = c;
		}
	}
	
	public void insert(Hanzi hanzi) {
		int unicode = hanzi.getCharcter().codePointAt(0);
		String unicodeString = String.valueOf(unicode);
		Node node = root;
		for(int i=0; i<unicodeString.length(); i++) {
			char c = unicodeString.charAt(i);
			int index = Character.getNumericValue(c);
			if(node.children[index]!=null) {
				node = node.children[index];
			}else {
				node.children[index]= new Node(c);
				node = node.children[index];
			}
		}
		node.hanzi = hanzi;
	}
	
	public Hanzi search(int unicode) {
		Node node = root;
		String unicodeString = String.valueOf(unicode);
		for(int i=0; i<unicodeString.length(); i++) {
			char c = unicodeString.charAt(i);
			int index = Character.getNumericValue(c);
			if(node.children[index]!=null) {
				node = node.children[index];
			}else {
				return null;
			}
		}
		return node.hanzi;
	}
}
