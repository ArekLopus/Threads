package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//applyToEither() will complete provided method when first of the two underlying futures complete
public class ApplyToEither {

	public ApplyToEither() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		
		//CompletableFuture<String> firstDone = int1.applyToEither(int2, this::conInt);
		CompletableFuture<String> firstDone = int1.applyToEitherAsync(int2, this::consumeInt);
		
		System.out.println("First done: "+firstDone.get());
		ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
	}
	
	private String consumeInt(Integer intt) {
		System.out.println("consumeInt()\t"+intt+", "+Thread.currentThread().getName());
		return String.valueOf(intt);
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
		new ApplyToEither();
	}
	
}
