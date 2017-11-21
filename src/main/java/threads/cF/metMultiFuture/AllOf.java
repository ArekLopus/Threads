package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//allOf() takes a variable number of completable futures and yield a CF<Void> that completes when all of them completes.
public class AllOf {

	public AllOf() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int3 = CompletableFuture.supplyAsync(this::getInt);
		
		CompletableFuture<Void> allCompleted = CompletableFuture.allOf(int1, int2, int3); 
		
		//callback on the resulting Future. Non-blocking, will run after completion of allOf()
		allCompleted.thenRun( () -> {			 
			System.out.println(int1.join()+", "+Thread.currentThread().getName());
			System.out.println(int2.join()+", "+Thread.currentThread().getName());
			System.out.println(int3.join()+", "+Thread.currentThread().getName());
		} );
		
		System.out.println(allCompleted.get());
		
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
		new AllOf();
	}
	
}
