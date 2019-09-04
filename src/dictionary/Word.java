/* 
 * NAME: AN NGUYEN
 * STUDENT ID: 1098402
 */

package dictionary;


public class Word {
	private String text;
	private String meaning;
	
	public Word(String text, String meaning) {
		this.text = text;
		this.meaning = meaning;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getMeaning() {
		return this.meaning;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
