package dictionary;

import java.util.ArrayList;

public interface WordStorage {
	public boolean add(String text, ArrayList<String> meanings);
	public boolean remove(String text);
	public Word search(String text);
	public void display();
}
