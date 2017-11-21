package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReturnNullTest {

	public ReturnNullTest() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync( () -> {
			return "Arek"; 
		}  , es); 
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync( () -> {
			return null; 
		}  , es); 
		
		
		
		System.out.println(cf1.get());
		System.out.println(cf2.get());
		
		if(cf2.get() == null) {
			System.out.println("null returned");
		}
		
		es.shutdown();
		
	}
    
	
	public static void main(String[] args) throws Exception {
		new ReturnNullTest();
	}
	
}
