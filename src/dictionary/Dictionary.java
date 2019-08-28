package dictionary;


public class Dictionary {
	WordStorage wordStorage;
	
	public Dictionary(WordStorage wordStorage) {
		this.wordStorage = wordStorage;
	}
	
	public synchronized boolean addNewWord(String text, String meaning) {
		return this.wordStorage.add(text, meaning);
	}
	
	public synchronized Word searchWord(String text) {
		return this.wordStorage.search(text);
	}
	
	public synchronized boolean deleteWord(String text) {
		return this.wordStorage.remove(text);
	}
}
