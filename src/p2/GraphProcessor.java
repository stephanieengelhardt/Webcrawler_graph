import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
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

	//graph data is a file name
	public GraphProcessor(String graphData) throws FileNotFoundException {
		this.map = new HashMap<String, ArrayList<String>>();
		File file = new File(graphData);
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
		HashMap<String, Integer > visited = new HashMap<String, Integer>();
		for(String s: map.keySet()){
			visited.put(s,0);
		}
	    ArrayList<String> path= new ArrayList<String>();
	    Queue<String> queue= new LinkedList<String>();
	    HashMap<String, String> parent = new HashMap<String, String>();
	    if(u.equals(v)) {
	    		path.add(u);
	    		return path;
	    }
	    if(map.size() > 0){
	        queue.add(u);
	        visited.put(u,1);
	    }
	    while(!(queue.isEmpty())){
	        String temp= queue.remove();
	        ArrayList<String> edges= map.get(temp);
	        for(String s: edges){
	        		if(s.equals(v)) {
	        			parent.put(v, temp);
	        			return pathMaker(parent, u,v);
	        		}
	            if(visited.get(s).equals(0)){
	                parent.put(s, temp);
	                queue.add(s);
		            visited.put(s,1);
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
		for(String current: map.keySet()) {
			for(String key: map.keySet()) {
				//it wouldn't be the longest path if it is a path to itself
				if(!key.equals(current)) {
					ArrayList<String> result= bfsPath(current,key);
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
		return max-1;
	}
	
	public int centrality(String v) {
		int count=0;
		if(!map.containsKey(v)) {
			return 0;
		}
		for(String current: map.keySet())
			if(!current.equals(v)) {
				for(String key: map.keySet())
					if(!key.equals(current)) {
						ArrayList<String> result= bfsPath(current,key);
						if(result.contains(v)) {
							count++;
						}
					}
				
		}
		//add one to account for it being the shortest path to itself
		return count+1;
	}
}



