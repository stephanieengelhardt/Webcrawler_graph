import java.util.ArrayList;

/*
 * @author Joshua Forest and Stephanie Engelhardt
 */
public class WikiCrawler {
	//We MUST use this or we get 0 credit
	private static final String BASE_URL= "https://en.wikipedia.org";
	ArrayList<String> links;
	int max;
	
	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName) {
		
	}
	
	public ArrayList<String> extractLinks(String doc){
		return null;
	}
	
	public void crawl() throws InterruptedException {
		for(int i=0; i<max; i++) {
			String URL= BASE_URL+links.get(i);
			if(i%50==0) {
				//after 50 requests you MUST use Thread.sleep() (or we get 0 credit)
				Thread.sleep(3000);
			}
		}
	}
}
