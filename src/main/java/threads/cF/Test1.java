package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {

	public Test1() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();
		
		ExecutorService es = Executors.newFixedThreadPool(4*4);
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( this::getInt , es)
				.thenApply( this::getInt )
				.thenApplyAsync( this::getInt , es); 
		
		System.out.println(cf.get());
		
		System.out.println((System.nanoTime() - start) / 1_000_000);
		
		es.shutdown();
		
	}
    
	private Integer getInt() {
		//int sleep = (ThreadLocalRandom.current().nextInt(7)+1)*100;
		int sleep = 1000;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getIn1t()\t"+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	private Integer getInt(int i) {
		//int sleep = (ThreadLocalRandom.current().nextInt(7)+1)*100;
		int sleep = 1000;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getIn1t()\t"+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
	public static void main(String[] args) throws Exception {
		new Test1();
	}
	
}
