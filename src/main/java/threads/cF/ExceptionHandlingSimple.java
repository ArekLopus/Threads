package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ExceptionHandlingSimple {

	public ExceptionHandlingSimple() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool) 
				throw new RuntimeException("ERROR!");
			return 12345;
		}, es);
		
		CompletableFuture<Integer> fixed = cf.exceptionally( ex -> { return -1; } );
		
		if(fixed.get() < 0) {
			System.out.println("-----Exception was thrown!!! Returns: "+fixed.get()+"\n");
		} else {
			System.out.println("Supplied: "+fixed.get()+"\n");
		}
		
		System.out.println("fixed - isCompletedExceptionally?:\t"+fixed.isCompletedExceptionally());
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new ExceptionHandlingSimple();
	}
	
}
