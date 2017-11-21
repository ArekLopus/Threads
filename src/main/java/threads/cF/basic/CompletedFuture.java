package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//static completedFuture(U value) - Returns a new CompletableFuture that is already completed with the given value.
public class CompletedFuture {

	public CompletedFuture() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			System.out.println("supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		
		System.out.println("First:\t"+cf.get());
		
		cf.complete("Arek2");
		System.out.println("Second:\t"+cf.get());
		
		cf = CompletableFuture.completedFuture("Arek3");
		System.out.println("Third:\t"+cf.get());
		
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new CompletedFuture();
	}
	
}
