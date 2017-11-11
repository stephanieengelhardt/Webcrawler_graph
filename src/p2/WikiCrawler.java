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
	private String fileName, seedURL;
	private int max;
	ArrayList<String> links;
	ArrayList<String> topics;
	Queue<String> queue= new LinkedList<String>();
	
	public static void main(String[] args) throws IOException {
		
		WikiCrawler w= new WikiCrawler("hello", 10, null, "test.txt");
		ArrayList<String> test= w.extractLinks("/wiki/Iowa_State_Cyclones");
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
		ArrayList<String> result= new ArrayList<String>();
		String line;
		//FileReader fileReader= new FileReader(doc);
		//BufferedReader br= new BufferedReader(fileReader);
		String page = BASE_URL + doc;
		URL url = new URL(page);
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader (new InputStreamReader(is));
		while((line=br.readLine())!=null) {
			if(line.contains("<P>") || line.contains("<p>")){
				//found first occurrence of <P> or <p>, so start saving links
				break;
			}
		}
		boolean done=false;
		while(!done) {
			result.addAll(extractFromString(line));
			if((line=br.readLine())==null) done=true;
		}
		br.close();
		return result;
	}
	
	private ArrayList<String> extractFromString(String line){
		ArrayList<String> result = new ArrayList<String>();
		String link;
		String remainingString="";
		boolean more = false;
		if(line.contains("<a href=\"/wiki")) { // add <a href "
			link=line.substring(line.indexOf("/wiki"));
			if(link.indexOf('"')>-1){
				more=true;
				remainingString=link.substring(link.indexOf('"'));
			}
			link=link.substring(0, link.indexOf('"'));
			if(!link.contains("#") && !link.contains(":")) result.add(link);
			if(more) result.addAll(extractFromString((remainingString)));
		}
		return result;
	}
	
	public void crawl() throws InterruptedException, IOException {
		//only find 0 to max amount of links within this webpage
		for(int i=0; i<max; i++) {
			if(i%50==0) {
				//after 50 requests you MUST use Thread.sleep() (or we get 0 credit)
				Thread.sleep(3000);
			}
			readPage(links.get(i));
		}
	}
	
	private ArrayList<String> readPage(String url){
	    URL start= new URL(BASE_URL+url);
	    InputStream read=start.openStream();
	    Buffered br= new BufferedReader(new InputStreamReder(read));
	    String page="";
	    String line=br.readLine();
	    while(line!=null){
	        page+=line+ '\n';
	    }
	    return extractFromString(page);
	}
	
	
}
