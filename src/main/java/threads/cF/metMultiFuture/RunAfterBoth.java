package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//runAfterBoth() works similarly to thenRun() but waits for two futures instead of one.
//Czeka na wykonanie 2 Future, ale NIE MA BEZPOŚREDNIO dostępu do wyników (jak thenCombine/thenAcceptBoth), zwraca CF<Void>
//Uzywane tylko jako info / aktywator czegoś po zakończeniu 2 Future
public class RunAfterBoth {

	public RunAfterBoth() throws InterruptedException, ExecutionException {
		
		CompletableFuture<ThreadLocalRandom> ranFut = CompletableFuture.supplyAsync(this::getRandom);
		CompletableFuture<Integer> intFut = CompletableFuture.supplyAsync(this::getInt);
		
		Runnable randomInt = () -> {
			System.out.println("Operation Successful");
			System.out.println("runAfterBothAsync() waits until 2 Futures complete and then runs returning void method."
					+ " No direct access to result of these 2 Futures.  Random int: "+ranFut.join().nextInt(intFut.join()));
		};
		
		//CompletableFuture<Void> ranInt = ranFut.runAfterBoth(intFut, () -> {System.out.println("Operation Successful");});
		//CompletableFuture<Void> ranInt = ranFut.runAfterBothAsync(intFut, randomInt);

		ranFut.runAfterBothAsync(intFut, randomInt);
		
		ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
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
		new RunAfterBoth();
	}
	
}
