package com.tiger.CharacterPalace.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tiger.CharacterPalace.model.LmTrie;
import com.tiger.CharacterPalace.service.character.LanguageModelColorSelector;
import com.tiger.CharacterPalace.util.FileManager;

class ColorSelectorTest {
    private LanguageModelColorSelector colorSelector;

    @BeforeEach
    void setUp() {
        LmTrie trie = FileManager.fileToTrie("glove.6B.100d.txt");

        colorSelector = new LanguageModelColorSelector(trie);
    }

    @Test
    void testSelectColor() {
        String[] word1 = {"apple"};
        String[] word2 = {"rain"};
        String[] word3 = {"car"};
        String[] word4 = {"girl"};
        String[] word5 = {"man"};
        String[] word6 = {"happy"};
        String[] word7 = {"weird"};
        String[] word8 = {"strong"};
        String[] word9 = {"cool"};
        String[] word10 = {"man","adjective"};

        System.out.println(word1[0]+" "+colorSelector.selectColor(word1));
        System.out.println(word2[0]+" "+colorSelector.selectColor(word2));
        System.out.println(word3[0]+" "+colorSelector.selectColor(word3));
        System.out.println(word4[0]+" "+colorSelector.selectColor(word4));
        System.out.println(word5[0]+" "+colorSelector.selectColor(word5));
        System.out.println(word6[0]+" "+colorSelector.selectColor(word6));
        System.out.println(word7[0]+" "+colorSelector.selectColor(word7));
        System.out.println(word8[0]+" "+colorSelector.selectColor(word8));
        System.out.println(word9[0]+" "+colorSelector.selectColor(word9));
        System.out.println(word10[0]+" "+colorSelector.selectColor(word10));

        
    }

   
}
