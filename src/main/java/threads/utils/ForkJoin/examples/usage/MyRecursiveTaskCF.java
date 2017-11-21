package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRecursiveTaskCF {
	
	int counter;
	
	public MyRecursiveTaskCF(int doIt) {

		//java.util.concurrent.ForkJoinPool es = new ForkJoinPool(4*400);
		ExecutorService es = Executors.newFixedThreadPool(400);
		
		List<Integer> list = new ArrayList<>();
		for(Integer i=0; i<doIt; i++) {
			list.add(100);
		}
		
		long start = System.nanoTime();
		
		CompletableFuture<?>[] cfa = list.stream()
				.map( i -> CompletableFuture.supplyAsync( () -> doIt(i), es)  )
				.toArray(CompletableFuture[]::new);
    	
		CompletableFuture.allOf(cfa).join();
		
		for(int i=0; i<doIt; i++) {
			counter += (int)cfa[i].join();
		}
		
		System.out.println("Counter: "+counter);
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		
		es.shutdown();
    }


	public static void main(String[] args) {
		new MyRecursiveTaskCF(400);
		

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
