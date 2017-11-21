package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//Like thenAccept() but when you have 2 tasks yielding result of the same type
//and you only care about response time, not which task resulted first.
public class AcceptEither {

	public AcceptEither() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		
		//int1.acceptEither(int2, this::conInt);
		int1.acceptEitherAsync(int2, this::consumeInt);
		
		ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
	}
	
	private void consumeInt(Integer intt) {
		System.out.println("consumeInt:\t"+intt+", "+Thread.currentThread().getName());
	}
	
	
	private Integer getInt() {
		int sleep = (ThreadLocalRandom.current().nextInt(7)+1)*100;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getIn1t()\t"+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
	public static void main(String[] args) throws Exception {
		new AcceptEither();
	}
	
}
