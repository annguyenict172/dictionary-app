package dictionary;

import java.util.ArrayList;

public class BSTWordStorage implements WordStorage {
	public Node root;
	
	public BSTWordStorage() {
		this.root = null;
	}
	
	public boolean add(String text, String meaning) {
		if (this.search(text) != null) {
			return false;
		}
		Node newNode = new Node(new Word(text, meaning));
		root = this.addRecursively(this.root, newNode);
		return true;
	}
	
	public Node addRecursively(Node current, Node newNode) {
		if (current == null) {
	        return newNode;
	    }
		if (newNode.compareWith(current) < 0) {
			current.left = this.addRecursively(current.left, newNode);
		} else {
			current.right = this.addRecursively(current.right, newNode);
		}
		return current;
	}
	
	public boolean remove(String text) {
		if (this.search(text) == null) {
			return false;
		}
		this.root = removeRecursively(this.root, text);
		return true;
	}
	
	public Node removeRecursively(Node current, String text) {
		if (current == null) return null;
		if (current.compareWith(text) == 0) {
			if (current.left == null && current.right == null) {
				return null;
			} else if (current.left == null) {
				return current.right;
			} else {
				return current.left;
			}
		} else if (current.compareWith(text) < 0) {
			current.right = removeRecursively(current.right, text);
		} else {
			current.left = removeRecursively(current.left, text);
		}
		return current;
	}
	
	public Word search(String text) {
		Node node = this.searchNodeRecursively(this.root, text);
		if (node == null) return null;
		return node.word;
	}
	
	public Node searchNodeRecursively(Node current, String text) {
		if (current == null) return null;
		
		if (current.compareWith(text) == 0) {
			return current;
		} else if (current.compareWith(text) > 0) {
			return this.searchNodeRecursively(current.left, text);
		} else {
			return this.searchNodeRecursively(current.right, text);
		}
	}
	
	public void display() {
		this.displayRecursively(this.root);
	}
	
	public void displayRecursively(Node node) {
		if (node == null) return;
		this.displayRecursively(node.left);
		System.out.println(node.getValue());
		this.displayRecursively(node.right);
	}
}
