package threads.cF.metMultiFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

//The trick is to use existing allOf() but when allDoneFuture completes (which means all underlying futures are done),
//simply iterate over all futures and join() on each. However this call is guaranteed not to block coz all futures completed!
public class AllOfGetingAsList {

	public AllOfGetingAsList() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt);
		CompletableFuture<Integer> int3 = CompletableFuture.supplyAsync(this::getInt);
		
		CompletableFuture<List<Integer>> fci =  sequence(Arrays.asList(int1, int2, int3));
		
		List<Integer> list = fci.get();
		
		System.out.println(list);
		
	}
	
	private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futs) {
		CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futs.toArray(new CompletableFuture[futs.size()]));
	    return allDoneFuture.thenApply( v ->
	            futs.stream()
	            	.map(future -> future.join())
	            	.collect(Collectors.<T>toList())
	    );
	}
	
	private Integer getInt() {
		int sleep = (ThreadLocalRandom.current().nextInt(10)+1)*100;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("From inside of getIn1t()\t"+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
	public static void main(String[] args) throws Exception {
		new AllOfGetingAsList();
	}
	
}
