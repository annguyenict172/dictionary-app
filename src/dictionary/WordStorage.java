/* 
 * NAME: CANH HA AN NGUYEN
 * STUDENT ID: 1098402
 */

package dictionary;

import java.io.IOException;


public interface WordStorage {
	public void loadFromURI(String uri) throws IOException;
	public boolean add(String text, String meaning);
	public boolean remove(String text);
	public Word search(String text);
	public void persist() throws IOException;
}
