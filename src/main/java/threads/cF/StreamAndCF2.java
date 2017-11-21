package threads.cF;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

import threads.cF.usage.Discount;
import threads.cF.usage.Quote;
import threads.cF.usage.Shop3;

public class StreamAndCF2 {
	
	List<Shop3> shops = Arrays.asList(new Shop3("Shop1"), new Shop3("Shop2"), new Shop3("Shop3"), new Shop3("Shop4"), new Shop3("Shop5"));
	//List<Shop3> shops = Arrays.asList(new Shop3("Shop1"), new Shop3("Shop2"), new Shop3("Shop3"), new Shop3("Shop4"), new Shop3("Shop5"), new Shop3("Shop6"), new Shop3("Shop7"), new Shop3("Shop8"));
	
	public StreamAndCF2() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();
		
		CompletableFuture<?>[] futures = findPricesStream("myPhone")
			.map( fc -> fc.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
			.toArray( size -> new CompletableFuture[size] );
		CompletableFuture.allOf(futures).join();
		
		long duration = (System.nanoTime() - start) / 1_000_000;
		
		System.out.println("\nDone in " + duration + " msecs");
		
	}

	public Stream<CompletableFuture<String>> findPricesStream(String product) {
	    return shops.stream()
	        .map( shop -> CompletableFuture.supplyAsync( () -> shop.getPrice(product), es) )
	        .map( fc -> fc.thenApply(Quote::parse) )
	        .map( fc -> fc.thenCompose(quote -> CompletableFuture.supplyAsync( () -> Discount.applyDiscount(quote), es) ));
	}
	
	private final Executor es = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
	    public Thread newThread(Runnable r) {
	        Thread t = new Thread(r);
	        t.setDaemon(true);
	        return t;
	    }
	});
	
	public static void main(String[] args) throws Exception {
		new StreamAndCF2();
	}
	
}
