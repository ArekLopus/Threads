package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutionAndCompletionException {

	public ExecutionAndCompletionException() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean check = true;
			if(check) throw new RuntimeException("Exc");
			return "Arek"; 
		}  , es); 
		
		try {
			
			//System.out.println(cf.get());		//ExecutionException
			System.out.println(cf.join());	//CompletionException
			
		} finally {
			es.shutdown();
		}
	}
    
	
	public static void main(String[] args) throws Exception {
		new ExecutionAndCompletionException();
	}
	
}
