package p2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

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
	
	public static void main(String[] args) throws FileNotFoundException {
		GraphProcessor g= new GraphProcessor("test.txt");
		/*for(String s: g.bfsPath("Minneapolis", "Omaha"))
			System.out.println(s);*/
		//A->M, A->C, M->C, M->O, M->A, C->M
		System.out.println("Centrality of Minneapolis: expected=6, actual="+g.centrality("Minneapolis"));
		//A->M, A->C, A->O, M->O, M->A, C->O, C->M, C->A
		System.out.println("Centrality of Ames: expected=8, actual="+g.centrality("Ames"));
		//A->M, A->C, M->C, M->O, M->A, C->M
		System.out.println("Centrality of Omaha: expected=3, actual="+g.centrality("Omaha"));
		//A->C, M->C, M->A, M->O, C->M, C->A, C->O
		System.out.println("Centrality of Chicago: expected=7, actual="+g.centrality("Chicago"));
	}
	//graph data is a file name
	public GraphProcessor(String graphData) throws FileNotFoundException {
		this.map=createMap(graphData);
	}
	
	public int outDegree(String v) {
		return map.get(v).size();
	}
	
	public ArrayList<String> bfsPath(String u, String v){
	    ArrayList<String> path= new ArrayList<String>();
	    Queue<String> queue= new LinkedList<String>();
	    ArrayList<String> visited= new ArrayList<String>();
	    HashMap<String, String> parent = new HashMap();
	    if(map.size() > 0){
	        queue.add(u);
	        visited.add(u);
	    }
	    while(!(queue.isEmpty())){
	        String temp= queue.remove();
	        ArrayList<String> edges= map.get(temp);
	        for(String s: edges){
	        		if(s.equals(v)) {
	        			parent.put(v, temp);
	        			return pathMaker(parent, u,v);
	        		}
	            if(!(visited.contains(s))){
	                parent.put(s, temp);
	                queue.add(s);
		            visited.add(s);
	            }
	        }
	    }
		return path;
	}
	
	private ArrayList<String> pathMaker(HashMap<String, String> bfs, String u, String v){
		ArrayList<String> path= new ArrayList<String>();
		path.add(v);
		String s=bfs.get(v);
		while(!s.equals(u)) {
			path.add(s);
			s=bfs.get(s);
		}
		path.add(u);
		ArrayList<String> result= new ArrayList<String>();
		for(int i=path.size()-1; i>=0;i--) {
			result.add(path.get(i));
		}
		return result;
	}
	
	public int diameter() {
		//bfs over all nodes
		//take the longest path by taking the last entry in the return arrayList
		//know the distance
		//if unconnected then, distance is 2*vertices
		return 0;
	}
	
	public int centrality(String v) {
		int count=0;
		Iterator it= map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry current=(Map.Entry) it.next();
			if(!current.equals(v)) {
				for(Map.Entry entry: map.entrySet()) {
					if(!entry.equals(current)) {
						ArrayList<String> result= bfsPath((String) current.getKey(),(String) entry.getKey());
						if(result.contains(v)) {
							count++;
						}
					}
				}
			}
		}
		//shortest path from each vertex to each vertex 
		//(if it is in that, then add one to the centrality)
		//maybe have a prioritized vertex? to make a subtree
		return count;
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
			key=key.trim();
			//to node
			String value = current.substring(current.indexOf(' ') + 1, current.length());
			value=value.trim();
			
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


