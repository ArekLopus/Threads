package threads.cF.usage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class Shop2Find3CFwithCustomExecutor {
	
	List<Shop2> shops = Arrays.asList(new Shop2("Shop1"), new Shop2("Shop2"), new Shop2("Shop3"), new Shop2("Shop4"), new Shop2("Shop5"));
	//List<Shop2> shops = Arrays.asList(new Shop2("Shop1"), new Shop2("Shop2"), new Shop2("Shop3"), new Shop2("Shop4"), new Shop2("Shop5"), new Shop2("Shop6"), new Shop2("Shop7"), new Shop2("Shop8"));
	
	private final Executor es = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
	    public Thread newThread(Runnable r) {
	        Thread t = new Thread(r);
	        t.setDaemon(true);
	        return t;
	    }
	});
		
	public Shop2Find3CFwithCustomExecutor() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();
		System.out.println(findPrices("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("\nDone in " + duration + " msecs");
		
	}

	public List<Object> findPrices(String product) {
		List<CompletableFuture<Object>> list = shops.stream()
			.map( shop -> CompletableFuture.supplyAsync( () -> shop.getPrice(product), es) )
			.map( cf -> cf.thenApply(Quote::parse))
			.map( cf -> cf.thenCompose( quote -> CompletableFuture.supplyAsync( () -> Discount.applyDiscount(quote), es)))
			.collect(Collectors.toList());
	    		
		return list.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
	}
	
	public static void main(String[] args) throws Exception {
		new Shop2Find3CFwithCustomExecutor();
	}
	
}
