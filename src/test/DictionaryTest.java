package test;

import dictionary.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testHashmapWordStorage() {
		Dictionary dictionary = new Dictionary("hello.txt", new HashmapWordStorage());
		dictionary.addNewWord("carrot", new ArrayList<String>());
		dictionary.addNewWord("banana", new ArrayList<String>());
		dictionary.addNewWord("apple", new ArrayList<String>());
		
		
		assertEquals(dictionary.searchWord("apple").getText(), "apple");
		
		dictionary.deleteWord("apple");
		assertNull(dictionary.searchWord("apple"));
	}
	
	@Test
	void testBSTWordStorage() {
		Dictionary dictionary = new Dictionary("hello.txt", new HashmapWordStorage());
		dictionary.addNewWord("carrot", new ArrayList<String>());
		dictionary.addNewWord("banana", new ArrayList<String>());
		dictionary.addNewWord("apple", new ArrayList<String>());
		
		
		assertEquals(dictionary.searchWord("apple").getText(), "apple");
		
		dictionary.deleteWord("apple");
		assertNull(dictionary.searchWord("apple"));
	}

}
