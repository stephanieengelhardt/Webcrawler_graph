package p2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/*
 * @author Joshua Forest and Stephanie Engelhardt
 * This represents the web where every node is a webpage
 * and if there is a directed edge from node p and q
 * that means that there is a link from page p to page q 
 * on the website
 */
public class GraphProcessor {
	private HashMap<String, ArrayList<String>> map;
	private int vertices; 
	
	//graph data is a file name
	public GraphProcessor(String graphData) {
		this.map=createMap(graphData);
	}
	
	public int outDegree(String v) {
		return 0;
	}
	
	public ArrayList<String> bfsPath(String u, String v){
	     ArrayList<String> path= new ArrayList<String>();
	    Queue<String> queue= new LinkedList<String>();
	    ArrayList<String> visited= new ArrayList<String>();
	    
	    if(map.size() > 0){
	    		String current=map.keySet().iterator().next();
	        queue.add(current);
	        visited.add(current);
	    }
	    while(!(queue.isEmpty())){
	        String temp= queue.remove();
	        ArrayList<String> edges= map.get(temp);
	        for(String s: edges){
	            if(!(visited.contains(s))){
	                if(!(path.contains(v))){
	                    path.add(s);
	                }
	                else{
	                    return path;
	                }
	                queue.add(s);
	                visited.add(s);
	            }
	        }
	    }
	    
		return path;
	}
	
	public int diameter() {
		return 0;
	}
	
	public int centrality(String v) {
		return 0;
	}
	

	private HashMap<String, ArrayList<String>> createMap(String filename) throws FileNotFoundException{
		HashMap<String, ArrayList<String>> map = new HashMap();
		File file = new File(filename);
		Scanner scan=new Scanner(file);
		//first line indicates the number of vertices
		vertices = scan.nextInt();
		scan.nextLine();
		//the rest of the lines are directed edges
		while(scan.hasNextLine()){
			String current= scan.nextLine();
			//from node
			String key = current.substring(0, current.indexOf(' '));
			//to node
			String value = current.substring(current.indexOf(' ') + 1, current.length());
			if(map.containsKey(key))
				map.get(key).add(value);
			else{
				map.put(key, new ArrayList<String>());
				map.get(key).add(value);
			}
			if(!map.containsKey(value))
				map.put(value, new ArrayList<String>());
		}
		scan.close();
		return map;
	}
}
