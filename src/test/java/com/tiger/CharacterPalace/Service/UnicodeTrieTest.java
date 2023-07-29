package com.tiger.CharacterPalace.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.Hanzi;
import com.tiger.CharacterPalace.model.UnicodeTrie;

public class UnicodeTrieTest {

    @Test
    public void testInsertAndSearch() {
        UnicodeTrie trie = new UnicodeTrie();

        Hanzi hanzi1 = new Hanzi();
        hanzi1.setCharcter("白");
        Hanzi hanzi2 = new Hanzi();
        hanzi2.setCharcter("木");

        trie.insert(hanzi1);
        trie.insert(hanzi2);

        assertEquals(hanzi1, trie.search(hanzi1.getCharcter().codePointAt(0)));
        assertEquals(hanzi2, trie.search(hanzi2.getCharcter().codePointAt(0)));
    }
}

