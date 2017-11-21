package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompleteLongCompManually {

	public CompleteLongCompManually() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync( () -> {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Arek"; 
		}  , es); 
		
		cf1.complete("Completed Earlier");
		cf1.thenAccept(System.out::println);
		
		es.shutdown();
		
	}
    
	
	public static void main(String[] args) throws Exception {
		new CompleteLongCompManually();
	}
	
}
