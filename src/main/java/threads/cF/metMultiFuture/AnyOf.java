package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//anyOf() takes a variable number of completable futures and yield a CF<Object> that completes when any of them completes
public class AnyOf {

	public AnyOf() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int3 = CompletableFuture.supplyAsync(this::getInt);
		
		CompletableFuture<Object> cf = CompletableFuture.anyOf(int1, int2, int3); 
		
		System.out.println(cf.get()+", "+cf.get().getClass());
		
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
		new AnyOf();
	}
	
}
