package threads.cF;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	public Test() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			return "Arek"; 
		}  , es); 
		

		System.out.println(cf.get());
		
		CompletableFuture.runAsync( () -> { System.out.println("Arek"); } );
		
		
		es.shutdown();
		
	}
    
	
	public static void main(String[] args) throws Exception {
		new Test();
	}
	
}
