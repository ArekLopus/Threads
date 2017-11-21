package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//T get(timeout) throws InterruptedExc, ExecutionExc, TimeoutExc - Waits max declared amount of time and if not completed throws TimeoutException 
public class GetAndTimeout {

	public GetAndTimeout() {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			int sleep = (ThreadLocalRandom.current().nextInt(2)+1)*100;
			System.out.println("Sleeps: "+sleep);
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Arek"; 
		}); 
		
		try {					//Takes a lot of more time when ExecutorService used (timeout needs like 190ms)
			System.out.println("get(timeout) throws checked InterruptedException, ExecutionException, TimeoutException."
					+ "\n\nget(timeout) completed successfully, returns: "+cf.get(110, TimeUnit.MILLISECONDS));
			System.out.println("isCompletedExceptionally?: "+cf.isCompletedExceptionally());
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			System.out.println("\nget(timeout) NOT completed successfully, returns: ");
			e.printStackTrace();
		}
		
		es.shutdown();
	}
    
	
	public static void main(String[] args) throws Exception {
		new GetAndTimeout();
	}
	
}
