package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//isCompletedExceptionally() - Returns true if this CompletableFuture completed exceptionally, in any way.
public class IsCompletedExceptionally {

	public IsCompletedExceptionally() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!"); 
			System.out.println("From CF1.supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("Nie działa, bo nie działa :P"); 
			System.out.println("From CF2.supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		cf.cancel(true);	//cancel() has the same effect as completeExceptionally(new CancellationException()).
		
		Thread.sleep(100);
		
		System.out.println("CF1 - CompletedExceptionally?\t"+cf.isCompletedExceptionally()+" - always, because of cancel()");
		System.out.println("CF2 - CompletedExceptionally?\t"+cf2.isCompletedExceptionally()+" - randomly");
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new IsCompletedExceptionally();
	}
	
}
