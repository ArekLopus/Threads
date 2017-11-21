package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

//getNumberOfDependents() - Returns the estimated number of CFs whose completions are awaiting completion of this CF.
public class GetNumberOfDependents {

	public GetNumberOfDependents() throws InterruptedException, ExecutionException {
		
		CompletableFuture<ThreadLocalRandom> ranFut = CompletableFuture.supplyAsync(this::getRandom);
		CompletableFuture<Integer> intFut = CompletableFuture.supplyAsync(this::getInt);
		
		CompletableFuture<Integer> ranInt = ranFut.thenCombineAsync(intFut, this::randomInt);
		ranFut.thenCombineAsync(intFut, this::randomInt);
		ranFut.thenCombineAsync(intFut, this::randomInt);
		
		System.out.println("getNumberOfDependents(): "+ranFut.getNumberOfDependents()+"\n");
		
		System.out.println("Random int: "+ranInt.get());
	}
	
	private int randomInt(ThreadLocalRandom ran, Integer intt) {
		System.out.println("randomInt()\t"+Thread.currentThread().getName());
		return ran.nextInt(intt);
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
		new GetNumberOfDependents();
	}
	
}
