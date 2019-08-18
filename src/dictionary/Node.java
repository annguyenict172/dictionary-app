package dictionary;

public class Node {
	public Word word;
	public Node left;
	public Node right;
	
	public Node(Word word) {
		this.word = word;
		left = null;
		right = null;
	}
	
	public String getValue() {
		return this.word.getText();
	}
	
	public int compareWith(Node node) {
		return this.getValue().compareTo(node.getValue());
	}
	
	public int compareWith(String text) {
		return this.getValue().compareTo(text);
	}
}
