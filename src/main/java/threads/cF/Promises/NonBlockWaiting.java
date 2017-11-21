package threads.cF.Promises;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class NonBlockWaiting {
	
	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
	
	public NonBlockWaiting() throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> timeoutAfter = NonBlockWaiting.timeoutAfter(Duration.ofMillis(400));
		
		CompletableFuture<String> search = CompletableFuture.supplyAsync( () -> {
			int sleep = (ThreadLocalRandom.current().nextInt(6)+1)*100;
			System.out.println("Sleeping: "+sleep);
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Value";
		} );
		
		CompletableFuture<String> res = timeoutAfter.applyToEither(search, str ->  str  );
		
		CompletableFuture<String> fixed = res.exceptionally( ex -> "Search failed" );
		
		System.out.println(fixed.get());
		
		pool.shutdown();
	}
    
	public static CompletableFuture<String> timeoutAfter(Duration dur) {
	    CompletableFuture<String> promise = new CompletableFuture<>();
	    pool.schedule( () -> promise.completeExceptionally(new RuntimeException()), dur.toMillis(), TimeUnit.MILLISECONDS);
	    return promise;
	}
	
	public static void main(String[] args) throws Exception {
		new NonBlockWaiting();
	}	
}
