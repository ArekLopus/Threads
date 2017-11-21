package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class WhenComplete {

	public WhenComplete() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!");
			System.out.println("supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		
		
		cf.whenComplete(this::biConsumer);
		Thread.sleep(130);
		CompletableFuture<String> completed = cf.whenCompleteAsync(this::biConsumer);
		CompletableFuture<Void> totallyCompleted = completed.thenAccept( str -> System.out.println("totallyCompleted: "+str+", "+Thread.currentThread().getName()) );
		totallyCompleted.thenRun( () -> System.out.println("This is the end"+", "+Thread.currentThread().getName()) );
	}
	
	
	private <T> void biConsumer (T t, Throwable th) {
		if(t != null) {
			System.out.println("biConsumer(): "+t+", "+Thread.currentThread().getName());
		} else {
			System.out.println("biConsumer() ERROR: "+th.getMessage());
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		new WhenComplete();
	}
	
}
