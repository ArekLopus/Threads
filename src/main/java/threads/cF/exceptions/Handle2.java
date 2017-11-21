package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Handle2 {

	public Handle2() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!");
			System.out.println("supplyAsync() - "+Thread.currentThread().getName());
			return 12345;
		}, es);

		CompletableFuture<Integer> fixed1 = cf.handle( (el, ex) -> {
		    if (ex != null) {
		    	System.out.println("fixed1.handle: The Problem is: "+ ex+", "+Thread.currentThread().getName());
		        return -1;
		    } else {
		    	return el;
		    }
		});
		System.out.println("CompletableFuture<String> fixed1.get(): "+fixed1.get().getClass());
		System.out.println("CompletableFuture<String> fixed1.get(): "+fixed1.get());
		
		CompletableFuture<String> fixed2 = cf.handleAsync((el, ex) -> {
			if (ex != null) {
				System.out.println("fixed2.handleAsync: The Problem is: "+ ex+", "+Thread.currentThread().getName());
				return "Error"+", "+Thread.currentThread().getName();
			} else {
				return el+", "+Thread.currentThread().getName();
			}
		});
		System.out.println("CompletableFuture<String> fixed2.get(): "+fixed2.get().getClass());
		System.out.println("CompletableFuture<String> fixed2.get(): "+fixed2.get());
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new Handle2();
	}
	
}
