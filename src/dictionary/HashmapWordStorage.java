/* 
 * NAME: CANH HA AN NGUYEN
 * STUDENT ID: 1098402
 */

package dictionary;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class HashmapWordStorage implements WordStorage {
	private HashMap<String, Word> mapper;
	
	public HashmapWordStorage() {
		this.mapper = new HashMap<String, Word>();
	}
	
	public void loadFromURI(String uri) throws IOException {
		try {
			JSONObject jsonDictionary = (JSONObject) new JSONParser().parse(new FileReader(uri)); 
			ArrayList<String> words = new ArrayList<String>(jsonDictionary.keySet());
			for (String word : words) {
			    this.add(word, (String) jsonDictionary.get(word)); 
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public boolean add(String text, String meaning) {
		if (mapper.get(text) != null) {
			return false;
		}
		mapper.put(text, new Word(text, meaning));
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
	
	public void persist() throws IOException {
		
	}
}
