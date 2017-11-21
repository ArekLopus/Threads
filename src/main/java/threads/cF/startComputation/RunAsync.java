package threads.cF.startComputation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class RunAsync {

	public RunAsync() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();

		CompletableFuture.runAsync( () -> {
			int sleep = doNothing();
			System.out.println(sleep+", runAsync() - "+Thread.currentThread().getName());
		} );

		CompletableFuture.runAsync( () -> {
			int sleep = doNothing();
			System.out.println(sleep+", runAsync() with Executor - "+Thread.currentThread().getName());
		} , es);
		
		System.out.println("This is async");
	}
	
	public int doNothing() {
		int sleep = (ThreadLocalRandom.current().nextInt(7)+1)*100;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return sleep;
	}
	
	public static void main(String[] args) throws Exception {
		new RunAsync();
	}
	
}
