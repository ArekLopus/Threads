package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRecursiveTaskParallel {
	
	int counter;
	
	public MyRecursiveTaskParallel(int doIt) {

		//java.util.concurrent.ForkJoinPool es = new ForkJoinPool(4*400);
		ExecutorService es = Executors.newFixedThreadPool(400);
		
		List<Integer> list = new ArrayList<>();
		for(Integer i=0; i<doIt; i++) {
			list.add(100);
		}
		
		long start = System.nanoTime();
		
		list.parallelStream().forEach( this::doIt );
		
		System.out.println("Counter: "+counter);
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		
		es.shutdown();
    }


	public static void main(String[] args) {
		new MyRecursiveTaskParallel(4);
		

	}
	
	private Integer doIt(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Sleeps: "+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
}
