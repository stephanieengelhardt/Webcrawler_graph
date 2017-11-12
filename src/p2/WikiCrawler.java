package p2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.net.*;
import java.io.*;
/*
 * @author Joshua Forest and Stephanie Engelhardt
 */
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

	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		WikiCrawler w= new WikiCrawler("hello", 10, null, "test.txt");
		String t = w.getHTML("/wiki/Iowa_State_Cyclones");
		ArrayList<String> test= w.extractLinks(t);
		for(String s: test) {
			System.out.println(s);
		}	
	}


	
	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) {
		this.seedURL = seedUrl;
		this.max=max;
		this.topics=topics;
		this.fileName=fileName;
	}
	
	public ArrayList<String> extractLinks(String doc) throws IOException{
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
	
	private String getHTML(String link) throws InterruptedException, IOException{
		String website = BASE_URL+link;
		URL url= new URL(website);
		InputStream read= url.openStream();
		BufferedReader br= new BufferedReader(new InputStreamReader(read));
		count ++;
		if(count%50==0) {
			//after 50 requests you MUST use Thread.sleep() (or we get 0 credit)
			Thread.sleep(3000);
		}
		String line;
		String doc="";
		while((line=br.readLine()) != null){
			doc+=line;
		}
		return doc;
	}
	
	public void crawl() throws InterruptedException, IOException {
		//only find 0 to max amount of links within this webpage
		Queue<String> q = new LinkedList<String>();
		LinkedList<String> l=new LinkedList<String>();
		for(int i=0; i<max; i++) {
			String doc = getHTML(seedURL);
			for(String s : extractLinks(doc)){
				q.add(doc);
			}

		}
	}
	
	
	
}
