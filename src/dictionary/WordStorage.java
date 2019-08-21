package dictionary;


public interface WordStorage {
	public boolean add(String text, String meaning);
	public boolean remove(String text);
	public Word search(String text);
	public void display();
}
