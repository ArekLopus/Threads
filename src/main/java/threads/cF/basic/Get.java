package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//T get() throws InterruptedExc, ExecutionExc - Waits if necessary for this future to complete, and then returns its result. 
public class Get {

	public Get() {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!");
			try {
				System.out.println("get() blocks until Future returns");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Arek"; 
		}, es); 
		
		CompletableFuture<String> chechIfExc = cf.exceptionally( ex -> "We have a problem: "+((Throwable) ex).getMessage()+", "+Thread.currentThread().getName() );
		
		try {
			System.out.println("Unlike join(), get() throws checked InterruptedExc & ExecutionExc"
					+ " so it needs try/catch or throws clause.\n\nget() returns: "+chechIfExc.get());
			System.out.println("isCompletedExceptionally?: "+cf.isCompletedExceptionally());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		es.shutdown();
	}
    
	
	public static void main(String[] args) throws Exception {
		new Get();
	}
	
}
