package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ExceptionHandlineIn1Line {

	public ExceptionHandlineIn1Line() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		CompletableFuture<Integer> fixed = CompletableFuture.supplyAsync( this::supplyInt, es)
				.exceptionally( ex -> { return -1; } );
		
		if(fixed.get() < 0) {
			System.out.println("-----Exception was thrown!!! Returns: "+fixed.get()+"\n");
		} else {
			System.out.println("Supplied: "+fixed.get()+"\n");
		}
		
		System.out.println("fixed - isCompletedExceptionally?:\t"+fixed.isCompletedExceptionally());
		
		es.shutdown();
	}
	

	private Integer supplyInt() {
		boolean bool = ThreadLocalRandom.current().nextBoolean();
		if(bool) 
			throw new RuntimeException("ERROR!");
		return 12345;
	}
	
	
	public static void main(String[] args) throws Exception {
		new ExceptionHandlineIn1Line();
	}
	
}
