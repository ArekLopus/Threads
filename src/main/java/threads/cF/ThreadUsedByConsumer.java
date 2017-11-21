package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadUsedByConsumer {

	public ThreadUsedByConsumer() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt, es);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt, es);
		
		int1.applyToEither(int2, this::consumeInt);
		int1.applyToEitherAsync(int2, this::consumeIntAsync, es);
		
		Thread.sleep(1000);
		es.shutdown();
	}
	
	private String consumeInt(Integer i) {
		System.out.println("consumeInt()\t"+i+", "+Thread.currentThread().getName()+" - When not using Async, consumer uses the same thread as the thread that finishes first");
		return String.valueOf(i);
	}
	private String consumeIntAsync(Integer i) {
		System.out.println("consumeInt()\t"+i+", "+Thread.currentThread().getName()+" - When using Async, consumer uses the thread supplied by the method");
		return String.valueOf(i);
	}
	
	
	private Integer getInt() {
		int sleep = (ThreadLocalRandom.current().nextInt(7)+1)*100;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getIn1t()\t"+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
	public static void main(String[] args) throws Exception {
		new ThreadUsedByConsumer();
	}
	
}
