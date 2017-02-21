package assignment3;

import java.util.ArrayList;

public class Node {
    private String data;
    private Node parent;
    private ArrayList<Node> children;
    
    public Node(String word) {
    	data = word;
    	parent = null;
    }
    
    public void addChildren(String word) {
    	Node newChild = new Node(word);
    	newChild.parent = this;
    	this.children.add(newChild);
    }
    
    public void addChildren(Node n) {
    	this.children.add(n);
    }

    public ArrayList<Node> getChildren() {
    	return children;
    }
    
    public int getNumChildren() {
    	return children.size();
    }
    
    public Node getNode(int index) {
    	return children.get(index);
    }
    
    public Node getParent() {
    	return parent;
    }
    
    public void setParent(Node p) {
    	parent = p;
    }
    
    public String getWord() {
    	return data;
    }
}