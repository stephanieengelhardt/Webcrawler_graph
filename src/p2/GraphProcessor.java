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

/**
 * @author Joshua Forest and Stephanie Engelhardt
 * This represents the web where every node is a webpage
 * and if there is a directed edge from node p and q
 * that means that there is a link from page p to page q 
 * on the website
 **/
public class GraphProcessor {
	private HashMap<String, ArrayList<String>> map;
	private int vertices;
	
	public static void main(String[] args) throws FileNotFoundException {
		GraphProcessor g= new GraphProcessor("test.txt");
		for(String s: g.bfsPath("hi", "bye"))
			System.out.println(s);
		//A->M, A->C, M->C, M->O, M->A, C->M, M->M
		System.out.println("Centrality of Minneapolis: expected=7, actual="+g.centrality("Minneapolis"));
		//A->M, A->C, A->O, M->O, M->A, C->O, C->M, C->A, A->A
		System.out.println("Centrality of Ames: expected=9, actual="+g.centrality("Ames"));
		//C->0, A->0, M->O, O->O
		System.out.println("Centrality of Omaha: expected=4, actual="+g.centrality("Omaha"));
		//A->C, M->C, M->A, M->O, C->M, C->A, C->O, C->C
		System.out.println("Centrality of Chicago: expected=8, actual="+g.centrality("Chicago"));
		System.out.println(g.diameter());
		
	}
	//graph data is a file name
	public GraphProcessor(String graphData) throws FileNotFoundException {
		this.map=createMap(graphData);
	}
	
	//if v doesn't exist, return -1
	public int outDegree(String v) {
		if(map.get(v)!=null)
			return map.get(v).size();
		else
			return -1;
	}
	
	public ArrayList<String> bfsPath(String u, String v){
		if(!map.containsKey(u) || !map.containsKey(v)) {
			return new ArrayList<String>();
		}
	    ArrayList<String> path= new ArrayList<String>();
	    Queue<String> queue= new LinkedList<String>();
	    ArrayList<String> visited= new ArrayList<String>();
	    HashMap<String, String> parent = new HashMap<String, String>();
	    if(u.equals(v)) {
	    		path.add(u);
	    		return path;
	    }
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
		Iterator it= map.entrySet().iterator();
		int max=0;
		while(it.hasNext()) {
			Map.Entry current=(Map.Entry) it.next();
			for(Map.Entry entry: map.entrySet()) {
				//it wouldn't be the longest path if it is a path to itself
				if(!entry.equals(current)) {
					ArrayList<String> result= bfsPath((String) current.getKey(),(String) entry.getKey());
					if(result.size()>max) {
						max=result.size();
					}
				}
			}
		}
		//if unconnected then, distance is 2*vertices
		if(max==0) {
			return 2*vertices;
		}
		//subtract one because max is the number of vertices and it should count the number of edges
		return max-1;
	}
	
	public int centrality(String v) {
		int count=0;
		if(!map.containsKey(v)) {
			return 0;
		}
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
		//add one to account for it being the shortest path to itself
		return count+1;
	}
	

	private HashMap<String, ArrayList<String>> createMap(String filename) throws FileNotFoundException{
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
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



