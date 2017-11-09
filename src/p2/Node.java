package p2;

public class Node {
	String data;
	Boolean visited; 
	
	public Node(String data, boolean visted) {
		this.data=data;
		this.visited=visited;
	}
	
	public String getData() {
		return data;
	}
	
	public Boolean getVisited(){
	    return visited;
	}
	
	public void setVisited(Boolean visit){
	    this.visited=visit;
	}
}
