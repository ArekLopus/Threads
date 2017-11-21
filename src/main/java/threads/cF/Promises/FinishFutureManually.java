package threads.cF.Promises;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinishFutureManually {

	public FinishFutureManually() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = FinishFutureManually.<String>never();
		
		CompletableFuture.runAsync( () -> {
			try {
				System.out.println(cf.get());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});
		cf.complete("Finished");
		//cf.completeExceptionally(new RuntimeException("ERROR!"));
		
		Thread.sleep(100);
		
		CompletableFuture<String> cf2 = FinishFutureManually.never2();
		System.out.println(cf2.get());
		
		
		es.shutdown();
	}
    
	public static <T> CompletableFuture<T> never() {
	    return new CompletableFuture<>();
	}
	public static CompletableFuture<String> never2() {
	    CompletableFuture<String> cf = new CompletableFuture<>();
	    cf.complete("123");
	    return cf;
	}
	
	public static void main(String[] args) throws Exception {
		new FinishFutureManually();
	}
	
}
