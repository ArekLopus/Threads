package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//thenAcceptBoth() works similarly to thenAccept() but waits for two futures instead of one.
//To samo co thenCombine() tyle, że metoda wywoływana po wykonaniu obu metod zwraca void
public class ThenAcceptBoth {

	public ThenAcceptBoth() throws InterruptedException, ExecutionException {
		
		CompletableFuture<ThreadLocalRandom> ranFut = CompletableFuture.supplyAsync(this::getRandom);
		CompletableFuture<Integer> intFut = CompletableFuture.supplyAsync(this::getInt);
		
		//CompletableFuture<Void> ranInt = ranFut.thenAcceptBoth(intFut, (ran, intt) -> {System.out.println(ran.nextInt(intt));});
		//CompletableFuture<Void> ranInt = ranFut.thenAcceptBothAsync(intFut, this::randomInt);
		ranFut.thenAcceptBothAsync(intFut, this::randomInt);
		
		ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
	}
	
	private void randomInt(ThreadLocalRandom ran, Integer intt) {
		System.out.println("randomInt()\t"+Thread.currentThread().getName());
		System.out.println("It is like thenCombine() (waits until 2 Futures complete and combines their results"
				+ " in randomInt()), but here it returns CF<Void>");
		System.out.println("Also it works similarly to thenAccept() but waits for two futures instead of one.  Random int: "+ran.nextInt(intt));
	}
	
	private ThreadLocalRandom getRandom() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getRandom()\t"+Thread.currentThread().getName());
		return ThreadLocalRandom.current();
	}
	private Integer getInt() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("getInt()\t"+Thread.currentThread().getName());
		return 10;
	}
	
	public static void main(String[] args) throws Exception {
		new ThenAcceptBoth();
	}
	
}
