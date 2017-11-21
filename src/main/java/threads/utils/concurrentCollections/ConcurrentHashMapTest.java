package threads.utils.concurrentCollections;

import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentHashMapTest {
	
	private ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
	//private java.util.HashMap<String, Integer> chm = new java.util.HashMap<>();
	private int i = 0;
	
	public ConcurrentHashMapTest() throws InterruptedException {
		chm.put("one", 0);
		runIt();
		Thread.sleep(200);
		//System.out.println("\nValue: "+chm.get("one"));
		System.out.println("\nValue: "+chm.size());
	}
	
	
	private void runIt() {
		
		Runnable r = () -> {
			//chm.put("one", i);
			chm.put(String.valueOf(i), i);
		};
		
		for(i=0; i<1000; i++) {
			new Thread(r).start();
		}
	}
	
	public static void main(String... args) throws Exception {
		new ConcurrentHashMapTest();
	}
	
}
