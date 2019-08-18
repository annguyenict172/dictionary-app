package dictionary;

import java.util.ArrayList;

public class Dictionary {
	WordStorage wordStorage;
	
	public Dictionary(String fileName, WordStorage wordStorage) {
		this.wordStorage = wordStorage;
		// open fileName
		// addWord
	}
	
	public void addNewWord(String text, ArrayList<String> meanings) {
		this.wordStorage.add(text, meanings);
	}
	
	public Word searchWord(String text) {
		return this.wordStorage.search(text);
	}
	
	public void deleteWord(String text) {
		this.wordStorage.remove(text);
	}
	
	public void display() {
		this.wordStorage.display();
	}
}
