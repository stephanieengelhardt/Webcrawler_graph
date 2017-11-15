import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.net.*;
import java.io.*;
/**
 * @author Joshua Forest and Stephanie Engelhardt
 **/
public class WikiCrawler {
	
	
	//We MUST use this or we get 0 credits
	private static final String BASE_URL= "https://en.wikipedia.org";
	private String fileName;
	private String seedURL;
	//private ArrayList<String> links;
	private ArrayList<String> topics;
	private int max;
	private int count;
	//private Queue<String> queue= new LinkedList<String>();
	private int td=0;

	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) {
		this.seedURL = seedUrl;
		this.max=max;
		this.topics=topics;
		this.fileName=fileName;
	}
	
	public ArrayList<String> extractLinks(String doc) {
		ArrayList<String> result = new ArrayList<String>();	
		if(doc.contains("<P>") && doc.contains("<p>")){
			doc=doc.substring(Math.min(doc.indexOf("<P>"), doc.indexOf("<p>")));
			}
		else if(doc.contains("<P>")){
			doc=doc.substring(doc.indexOf("<P>"));
		}
		else if(doc.contains("<p>")){
			doc=doc.substring(doc.indexOf("<p>"));
		}
		else{
			return result;
		}
		
		result=extractFromString(doc,result);
		return result;
	}
	
	private ArrayList<String> extractFromString(String line, ArrayList<String> result){
		String link;
		String remainingString="";
		boolean more = false;
		if(line.contains("<a href=\"/wiki")) {
			line=line.substring(line.indexOf("<a href=\"/wiki"));
			link=line.substring(line.indexOf("/wiki"));
			if(link.indexOf('"')>-1){
				more=true;
				remainingString=link.substring(link.indexOf('"'));
			}
			link=link.substring(0, link.indexOf('"'));
			if(!link.contains("#") && !link.contains(":") && !result.contains(link)) result.add(link);
			if(more) result=extractFromString(remainingString, result);
		}
		return result;
	}
	
	private String getHTML(String link) {
		try{
			long startTime = System.nanoTime();
		String website = BASE_URL+link;
		URL url= new URL(website);
		InputStream read= url.openStream();
		BufferedReader br= new BufferedReader(new InputStreamReader(read));
		count ++;
		if(count%50==0 && count!=0) {
			//after 50 requests you MUST use Thread.sleep() (or we get 0 credit)
			System.out.println("...waiting");
			Thread.sleep(10000);
			System.out.println("moving again");
		}
		String line;
		String doc="";
		while((line=br.readLine()) != null){
			doc+=line;
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		td+=(duration/1000000);
		return doc;
		}
		catch(Exception e){
			System.out.println("exception thrown");
			return null;
		}
	}
	
	private boolean containsTopics(String html){
		if(topics!=null){
		for(String s : topics){
			if(!html.contains(s)) return false;
		}}
		return true;
	}
	
	public void crawl() {
		//only find 0 to max amount of links within this webpage
		Queue<String> q = new LinkedList<String>(); //to queue the webpages for visits
		ArrayList<String> vertices = new ArrayList<String>(); // to store the saved vertices
		ArrayList<String[]> edges = new ArrayList<String[]>(); //to store the edges between vertices
		ArrayList<String> visited = new ArrayList<String>(); //all of the vertices that have been visited
		BufferedWriter bw = null;
		FileWriter fw = null;
		try{
		fw = new FileWriter(fileName);
		bw = new BufferedWriter(fw);
		//------------------------------------------------------------------------------
		String URL=seedURL;
		q.add(URL);
	
		while(!q.isEmpty() && vertices.size()!=max){
			String currentURL = q.poll();
			String html = getHTML(currentURL);
			visited.add(currentURL);
			if(containsTopics(html)){
				vertices.add(currentURL);
				for(String link:extractLinks(html)){
					String[] arr = {currentURL,link};
					edges.add(arr);
					if(!visited.contains(link)) q.add(link);
				}

			}
		}
		
		for(int i=0;i<edges.size();i++){
			if(!(vertices.contains(edges.get(i)[1])) || edges.get(i)[0].equals(edges.get(i)[1])){
				edges.remove(i);
				i--;
			}
		}
		bw.write(""+vertices.size());
		for(String[] arr:edges){
			bw.newLine();
			bw.write(arr[0]+" "+arr[1]);
		}
		}
		catch(Exception e){
			System.out.println("exception thrown");
			return;
		}
		finally{
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} 
			catch (Exception e) {
				System.out.println("exception thrown");
				return;
			}
		}
	}
	
	
	
}
