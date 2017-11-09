package p2;
import java.util.ArrayList;

/*
 * @author Joshua Forest and Stephanie Engelhardt
 * This represents the web where every node is a webpage
 * and if there is a directed edge from node p and q
 * that means that there is a link from page p to page q 
 * on the website
 */
public class GraphProcessor {
	private HashMap<String, ArrayList<String>> map;
	private ArrayList<ArrayList<String>> stronglyConnectedComponent;
	private int verticies; 
	
	public GraphProcessor(String graphData) {
		this.vertices=0;
		this.map=createMapFromFile(graphData);
		this.StronglyConnectedComponent=createStronglyConnected();
	}
	
	public int outDegree(String v) {
		return 0;
	}
	
	public ArrayList<String> bfsPath(String u, String v){
		return null;
	}
	
	public int diameter() {
		return 0;
	}
	
	public int centrality(String v) {
		return 0;
	}
	
	private ArrayList<ArrayList<String>> createStronglyConnected(){
	    ArrayList<ArrayList<String>> result= new ArrayList();
	    Stack<String> stack= new Stack();
	    //bit array
	    Node visited[] = new Node[vertices];
	    Iterator<String> iterator= map.keySet().iterator();
	    int index=0;
	    while(iterator.hasNext()){
	        try{
	            visited[index]=new Node(iterator.next(), false);
	            index++;
	        }
	        catch(ArrayIndexOutOfBoundsException e){
	            System.out.println("Error with number of vertices from the parent file");
	            return null;
	        }
	    }
	    for(int i=0; i <visited.length; i++){
	        if(visited[i] !=null){
	            visited[i].setVisited(false);
	        }
	    }
	}
}
