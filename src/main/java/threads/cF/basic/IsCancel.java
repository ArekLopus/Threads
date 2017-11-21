package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//isCancelled() - Returns true if this CompletableFuture was cancelled before it completed normally.
public class IsCancel {

	public IsCancel() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("Nie działa, bo nie działa :P"); 
			System.out.println("From CompletableFuture.supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		
		cf.cancel(true);
		System.out.println("isCancelled?: "+cf.isCancelled());
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new IsCancel();
	}
	
}
