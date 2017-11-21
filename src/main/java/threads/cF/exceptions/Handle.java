package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//MUST USE ANOTHER CompletableFuture to handle Exception, if you use the main CF it always throws Exception in your face.  
public class Handle {

	public Handle() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!");
			return 12345;
		}, es);
		
		CompletableFuture<Integer> fixed = cf.handle( (el, ex) -> {
		    if (ex == null) {
		    	System.out.println("Exception is NULL, Element == "+ el+", "+Thread.currentThread().getName()+"\n");
		    	return el;
		    } else {
		    	System.out.println("Exception is NOT NULL, Element == "+ el+", "+Thread.currentThread().getName()+"\n");
		    	return -1;
		    }
		});
		//System.out.println("CompletableFuture<String> fixed.get(): "+cf.get()); //using the main CF throws Exception in your face.
		System.out.println("CompletableFuture<String> fixed.get(): "+fixed.get());
		System.out.println("CompletableFuture<String> fixed.get(): "+fixed.get().getClass());
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new Handle();
	}
	
}
