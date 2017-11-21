package threads.cF.usage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class Shop1Find5CFwithCustomExecutor {
	
	//List<Shop> shops = Arrays.asList(new Shop("Shop1"), new Shop("Shop2"), new Shop("Shop3"), new Shop("Shop4"));
	List<Shop1> shops = Arrays.asList(new Shop1("Shop1"), new Shop1("Shop2"), new Shop1("Shop3"), new Shop1("Shop4"), new Shop1("Shop5"), new Shop1("Shop6"), new Shop1("Shop7"), new Shop1("Shop8"));
	
//Note that you’re creating a pool made of daemon threads. A Java program can’t terminate or exit while a normal thread
//is executing, so a leftover thread waiting for a never-satisfiable event causes problems. By contrast, marking a thread
//as a daemon means it can be killed on program termination. There’s no performance difference. 
	private final Executor es = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
	    public Thread newThread(Runnable r) {
	        Thread t = new Thread(r);
	        t.setDaemon(true);
	        return t;
	    }
	});
	
	public Shop1Find5CFwithCustomExecutor() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();
		System.out.println(findPrices("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("\nDone in " + duration + " msecs");
		
		
	}
	
	public List<String> findPrices(String product) {
		
		List<CompletableFuture<String>> priceFutures =
				shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(	() -> String.format("%s price is %.2f, %s",
						shop.getName(), shop.getPrice("Zupa"), Thread.currentThread().getName()) , es ))
				.collect(Collectors.toList());
		
		return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	public static void main(String[] args) throws Exception {
		new Shop1Find5CFwithCustomExecutor();
	}
	
}
