package threads.utils.synchronizedWrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;import java.util.concurrent.ThreadLocalRandom;


//Collections.synchronizedCollection	//Collections.synchronizedList			//Collections.synchronizedMap
//Collections.synchronizedNavigableMap	//Collections.synchronizedNavigableSet	//Collections.synchronizedSet
//Collections.synchronizedSortedMap		//Collections.synchronizedSortedSet
public class SynchreonizedViewsTest {
	
	private List<Integer> syncList;
	private List<Integer> list;
	
	public SynchreonizedViewsTest() throws InterruptedException {
		list = new ArrayList<>();
		syncList = Collections.synchronizedList(new ArrayList<>());
		
		notSync();
		Thread.sleep(50);
		System.out.println("NotSync Finished "+list.size());
		
		sync();
		Thread.sleep(50);
		System.out.println("Sync Finished "+syncList.size());
	}

	private void notSync() {
		for(int j=0; j<20000; j++) {
			new Thread( () -> {
				try {
					int sleep = ThreadLocalRandom.current().nextInt(10)+1;
					list.add(sleep);
					Thread.sleep(sleep);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				//list.add(123);
			}).start();
		}
	}
	
	private void sync() {
		for(int i=0; i<20000; i++) {
			new Thread( () -> {
				try {
					int sleep = ThreadLocalRandom.current().nextInt(10)+1;
					syncList.add(sleep);
					Thread.sleep(sleep);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				//syncList.add(123);
			}).start();
		}
	}


	
	
	public static void main(String[] args) throws InterruptedException {
		new SynchreonizedViewsTest();
	}

}
