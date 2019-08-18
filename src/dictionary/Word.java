package dictionary;

import java.util.ArrayList;

public class Word {
	private String text;
	private ArrayList<String> meanings;
	
	public Word(String text, ArrayList<String> meanings) {
		this.text = text;
		this.meanings = meanings;
	}
	
	public String getText() {
		return this.text;
	}
	
	public ArrayList<String> getMeanings() {
		return this.meanings;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void addMeaning(String meaning) {
		this.meanings.add(meaning);
	}
}
