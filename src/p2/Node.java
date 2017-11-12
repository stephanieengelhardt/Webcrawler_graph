package p2;

public class Node {
	String link, parentLink;
	
	public Node(String link, String parentLink) {
		this.link=link;
		this.parentLink=parentLink;
	}
	
	public String getData(){
	    return parentLink+link;
	}
}
