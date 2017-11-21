package threads.cF.metMultiFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

//thenCombine() combines two independent futures when they are both done:
public class ThenCombine {

	public ThenCombine() throws InterruptedException, ExecutionException {
		
		CompletableFuture<ThreadLocalRandom> ranFut = CompletableFuture.supplyAsync(this::getRandom);
		CompletableFuture<Integer> intFut = CompletableFuture.supplyAsync(this::getInt);
		
		//CompletableFuture<Integer> ranInt = ranFut.thenCombine(intFut, (ran, intt) -> ran.nextInt(intt));
		CompletableFuture<Integer> ranInt = ranFut.thenCombineAsync(intFut, this::randomInt);
		
		System.out.println("ranInt waits until 'ranFut' and 'intFut' complete and combines their results"
				+ " in randomInt(ThreadLocalRandom ran, Integer i).  Random int: "+ranInt.get());
	}
	
	private int randomInt(ThreadLocalRandom ran, Integer i) {
		System.out.println("randomInt()\t"+Thread.currentThread().getName());
		return ran.nextInt(i);
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
		new ThenCombine();
	}
	
}
