package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//runAfterEither() czeka na wykonanie 1 z 2 Future, NIE MA BEZPOŚREDNIO dostępu do wyników, zwraca CF<Void> 
//Uzywane tylko jako info / aktywator czegoś po zakończeniu 1 z 2 Future
public class RunAfterEither {

	public RunAfterEither() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		
		//int1.runAfterEither(int2, r);
		int1.runAfterEitherAsync(int2, r);
		
		ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
	}
	
	private Runnable r = () -> {
		System.out.println("Operation Successful");
	};
	
	
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
		new RunAfterEither();
	}
	
}
