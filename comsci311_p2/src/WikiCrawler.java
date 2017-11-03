import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.net.*;
import java.io.*;
/*
 * @author Joshua Forest and Stephanie Engelhardt
 */
public class WikiCrawler {
	
	public static void main(String[] args) throws IOException {
		
		/*//Testing the extractLinks
		WikiCrawler w= new WikiCrawler("hello", 10, null, "test.txt");
		ArrayList<String> test= w.extractLinks("test.txt");
		for(String s: test) {
			System.out.println(s);
		}*/
		
	}
	
	//We MUST use this or we get 0 credit
	private static final String BASE_URL= "https://en.wikipedia.org";
	ArrayList<String> links;
	int max;
	Queue queue= new LinkedList();
	
	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) {
		this.max=max;
	}
	
	public ArrayList<String> extractLinks(String doc) throws IOException{
		ArrayList<String> result= new ArrayList<String>();
		String line;
		FileReader fileReader= new FileReader(doc);
		BufferedReader br= new BufferedReader(fileReader);
		while((line=br.readLine())!=null) {
			if(line.contains("<P>") || line.contains("<p>")){
				//found first occurrence of <P> or <p>, so start saving links
				break;
			}
		}
		while((line=br.readLine())!=null) {
			if(!line.contains("#") && !line.contains(":") && line.contains("/wiki/")) {
				result.add(line);
				
			}
		}
		return result;
	}
	
	public void crawl() throws InterruptedException, IOException {
		
		//only find 0 to max amount of links within this webpage
		for(int i=0; i<max; i++) {
			String website= BASE_URL+links.get(i);
			URL url= new URL(website);
			if(i%50==0) {
				//after 50 requests you MUST use Thread.sleep() (or we get 0 credit)
				Thread.sleep(3000);
			}
			InputStream read= url.openStream();
			BufferedReader br= new BufferedReader(new InputStreamReader(read));
			String line=br.readLine();
		}
	}
}
