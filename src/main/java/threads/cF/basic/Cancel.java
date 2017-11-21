package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//cancellation is treated as just another form of exceptional completion.
//cancel() has the same effect as completeExceptionally(new CancellationException()).
public class Cancel {

	public Cancel() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!"); 
			System.out.println("From CompletableFuture.supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		
		cf.cancel(true);	//cancel() has the same effect as completeExceptionally(new CancellationException()).
		CompletableFuture<String> safe = cf.exceptionally( ex -> "We have a problem: "+((Throwable) ex).getMessage()
				+", "+Thread.currentThread().getName() );
		
		System.out.println("Because of cancel() it will always finish exceptionally: "+safe.get()+"\n");
		System.out.println("CompletedExceptionally?\t"+cf.isCompletedExceptionally());
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new Cancel();
	}
	
}
