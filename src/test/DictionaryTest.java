/* 
 * NAME: AN NGUYEN
 * STUDENT ID: 1098402
 */

package test;

import dictionary.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testHashmapWordStorage() {
		Dictionary dictionary = new Dictionary(new HashmapWordStorage());
		dictionary.addNewWord("carrot", "vegetable");
		dictionary.addNewWord("banana", "fruit");
		dictionary.addNewWord("apple", "fruit");
		
		
		assertEquals(dictionary.searchWord("apple").getText(), "apple");
		
		dictionary.deleteWord("apple");
		assertNull(dictionary.searchWord("apple"));
	}
	
	@Test
	void testBSTWordStorage() {
		Dictionary dictionary = new Dictionary(new HashmapWordStorage());
		dictionary.addNewWord("carrot", "vegetable");
		dictionary.addNewWord("banana", "fruit");
		dictionary.addNewWord("apple", "fruit");
		
		
		assertEquals(dictionary.searchWord("apple").getText(), "apple");
		
		dictionary.deleteWord("apple");
		assertNull(dictionary.searchWord("apple"));
	}

}
