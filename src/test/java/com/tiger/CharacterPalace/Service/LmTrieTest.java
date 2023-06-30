package com.tiger.CharacterPalace.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LmTrieTest {
    private LmTrie trie;

    @BeforeEach
    void setUp() {
        trie = new LmTrie();
    }

    @Test
    void testInsertAndSearch() {
        trie.insert("apple 0.1 0.2 0.3");
        trie.insert("banana 0.4 0.5 0.6");

        double[] appleEmbeddings = trie.search("apple");
        double[] bananaEmbeddings = trie.search("banana");

        assertNotNull(appleEmbeddings);
        assertNotNull(bananaEmbeddings);

        assertArrayEquals(new double[]{0.1, 0.2, 0.3}, appleEmbeddings, 0.0001);
        assertArrayEquals(new double[]{0.4, 0.5, 0.6}, bananaEmbeddings, 0.0001);
    }

    @Test
    void testSearchNonexistentWord() {
        trie.insert("apple 0.1 0.2 0.3");
        double[] embeddings = trie.search("banana");
        assertNull(embeddings);
    }
}


