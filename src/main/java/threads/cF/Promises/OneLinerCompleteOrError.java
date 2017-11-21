package threads.cF.Promises;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
//http://www.javacodegeeks.com/2014/12/asynchronous-timeouts-with-completablefuture.html
//Cleaner one would wrap original future and make sure it finishes within given time. Such operator is available in
//com.twitter.util.Future (Scala; called within()), however is missing in scala.concurrent.Future (supposedly inspired by
//the former). Let’s leave Scala behind and implement similar operator for CompletableFuture. It takes one future as
//input and returns a future that completes when underlying one is completed. However if it takes too long to complete
//the underlying future, exception is thrown:
public class OneLinerCompleteOrError {

	public OneLinerCompleteOrError() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> responseFuture = within( asyncCode(), Duration.ofMillis(150) );
		
		responseFuture
		    .thenAccept(this::send)
		    .exceptionally( throwable -> {
		    	System.out.println("ERROR");
		    	return null;
		    });
		
	}
    
	private CompletableFuture<Integer> asyncCode() {
		return CompletableFuture.supplyAsync( () -> {
			int sleep = (ThreadLocalRandom.current().nextInt(3)+1)*100;
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sleep;
		});
	}
	private void send(Integer i) {
		System.out.println("Consumed: "+i);
	}
	
	public static <T> CompletableFuture<T> within(CompletableFuture<T> future, Duration duration) {
	    CompletableFuture<T> timeout = failAfter(duration);
	    return future.applyToEither(timeout, Function.identity());
	}
	
	public static <T> CompletableFuture<T> failAfter(Duration duration) {
	    CompletableFuture<T> promise = new CompletableFuture<>();
	    scheduler.schedule(() -> {
	        //final TimeoutException ex = new TimeoutException("Timeout after " + duration);
	        final RuntimeException ex = new RuntimeException("Timeout after " + duration);
	        return promise.completeExceptionally(ex);
	    }, duration.toMillis(), TimeUnit.MILLISECONDS);
	    return promise;
	}
	
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) throws Exception {
		new OneLinerCompleteOrError();
	}
	
}
