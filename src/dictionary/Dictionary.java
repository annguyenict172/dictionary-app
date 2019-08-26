package dictionary;


public class Dictionary {
	WordStorage wordStorage;
	
	public Dictionary(WordStorage wordStorage) {
		this.wordStorage = wordStorage;
	}
	
	public void addNewWord(String text, String meaning) {
		this.wordStorage.add(text, meaning);
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
