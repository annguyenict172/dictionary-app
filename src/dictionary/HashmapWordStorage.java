package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class HashmapWordStorage implements WordStorage {
	private HashMap<String, Word> mapper;
	
	public HashmapWordStorage() {
		this.mapper = new HashMap<String, Word>();
	}
	
	public boolean add(String text, ArrayList<String> meanings) {
		if (mapper.get(text) != null) {
			return false;
		}
		mapper.put(text, new Word(text, meanings));
		return true;
	}
	
	public boolean remove(String text) {
		if (mapper.get(text) == null) {
			return false;
		}
		mapper.remove(text);
		return true;
	}
	
	public Word search(String text) {
		return mapper.get(text);
	}
	
	public void display() {
		ArrayList<String> sortedKeys = new ArrayList<String>(this.mapper.keySet());
		Collections.sort(sortedKeys);
		for (String key : sortedKeys) {
		    System.out.println(mapper.get(key).getText());
		}
	}
}
