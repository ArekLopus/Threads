package threads.cF.metMultiFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

//The trick is to use existing allOf() but when allDoneFuture completes (which means all underlying futures are done),
//simply iterate over all futures and join() on each. However this call is guaranteed not to block coz all futures completed!
public class AllOfGetingAsList2 {
	ExecutorService es = Executors.newFixedThreadPool(4);
	
	public AllOfGetingAsList2() throws InterruptedException, ExecutionException {
		
		CompletableFuture<Integer> int1 = CompletableFuture.supplyAsync(this::getInt, es);
		CompletableFuture<Integer> int2 = CompletableFuture.supplyAsync(this::getInt, es);
		CompletableFuture<Integer> int3 = CompletableFuture.supplyAsync(this::getInt, es);
		
		List<CompletableFuture<Integer>> futs = Arrays.asList(int1, int2, int3);
		CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(int1, int2, int3);  //Wykonuje Futures 
		
		CompletableFuture<List<Integer>> cf = allDoneFuture.thenApply( 	//Po wykonaniu Futures 
			v -> futs.stream()						//v nic nie robi? To tylko dla sygnatury metody.
        		.map(future -> future.join())		//Stream CompletableFuture<Integer> mapujemy na wynik
        		.collect(Collectors.toList())		//Zwracamy listę wyników
		);
		
		System.out.println(cf.get());		//zwraca listę Integer
		es.shutdown();
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
		new AllOfGetingAsList2();
	}
	
}
