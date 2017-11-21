package threads.cF.Promises;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FinishFutureManuallyExceptionally {
	
	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
	
	public FinishFutureManuallyExceptionally() throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> cf = FinishFutureManuallyExceptionally.timeoutAfter(Duration.ofMillis(400));
		
		CompletableFuture<String> fixed = cf.exceptionally( ex -> "Search failed, message: "+ex.getMessage() );
		
		System.out.println(fixed.get());
		

		pool.shutdown();
		
	}
    
	public static CompletableFuture<String> timeoutAfter(Duration dur) {
	    CompletableFuture<String> promise = new CompletableFuture<>();
	    pool.schedule( () -> promise.completeExceptionally(
	    		new RuntimeException("ERROR")), dur.toMillis(), TimeUnit.MILLISECONDS);
	    return promise;
	}
	
	public static void main(String[] args) throws Exception {
		new FinishFutureManuallyExceptionally();
	}
	
}
