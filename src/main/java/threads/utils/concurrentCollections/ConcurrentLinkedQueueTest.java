package threads.utils.concurrentCollections;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentLinkedQueueTest {
	
	private ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();	
	private LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue<>();
	
	public ConcurrentLinkedQueueTest() throws InterruptedException {
		for(int i=0; i<10000; i++) {
			clq.offer(i);
		}
		
		System.out.println("size: "+clq.size());
		
		Runnable r = () -> {
			clq.poll();
		};
		
		long start = System.currentTimeMillis();
		for(int i=0; i<10000; i++) {
			new Thread(r).start();;
		}
		Thread.sleep(50);
		System.out.println("size: "+clq.size()+", "+(System.currentTimeMillis()-start)+" ms");
		
		
		for(int i=0; i<10000; i++) {
			lbq.offer(i);
		}
		Runnable r2 = () -> {
			lbq.poll();
		};
		
		start = System.currentTimeMillis();
		for(int i=0; i<10000; i++) {
			new Thread(r2).start();;
		}
		Thread.sleep(50);
		System.out.println("size: "+lbq.size()+", "+(System.currentTimeMillis()-start)+" ms");
		
	}
	
	public static void main(String... args) throws Exception {
		new ConcurrentLinkedQueueTest();
	}
}
