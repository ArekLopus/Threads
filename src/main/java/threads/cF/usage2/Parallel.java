package threads.cF.usage2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Parallel {

	public Parallel() throws InterruptedException, ExecutionException {
		
		List<String> sites = Arrays.asList("www.google.com", "www.youtube.com", "www.yahoo.com", "www.msn.com");
		
		long start = System.currentTimeMillis();
    	
		sites.parallelStream().forEach( this::downloadSite );
		
    	System.out.println("--- All is Done in: "+(System.currentTimeMillis() - start)+" ms");
		
		
	}
    
	private String downloadSite(String site) {
	    site = "http://"+site;
		try {
	    	String pageText;
	    	URL url = new URL(site);
	    	URLConnection conn = url.openConnection();
	    	System.out.println("Downloading...");
	    	long start = System.currentTimeMillis();
	    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
	    	    pageText = reader.lines().collect(Collectors.joining("\n"));
	    	}
	    	System.out.println("Done in: "+(System.currentTimeMillis() - start)+" ms");
	        return pageText;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	public static void main(String[] args) throws Exception {
		new Parallel();
	}
	
}
