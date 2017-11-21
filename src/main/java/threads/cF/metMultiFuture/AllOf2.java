package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//allOf() takes a variable number of completable futures and yield a CF<Void> that completes when all of them completes.
public class AllOf2 {

	public AllOf2() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int3 = CompletableFuture.supplyAsync(this::getInt);
		
		//CompletableFuture.allOf(int1, int2, int3).thenRun( () -> { System.out.println("Finished"); } );
		//CompletableFuture.allOf(int1, int2, int3).thenAccept( (i) -> { System.out.println("Finished: "+i); } ); 
		
		CompletableFuture.allOf(int1, int2, int3).join();		//join() makes to wait untill all completed!
		System.out.println("Waiting");
		
		ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
	}
	
	private Integer getInt() {
		int sleep = (ThreadLocalRandom.current().nextInt(10)+1)*100;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getIn1t()\t"+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
	public static void main(String[] args) throws Exception {
		new AllOf2();
	}
	
}
