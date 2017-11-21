package threads.cF.usage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MyTest {

	public MyTest() throws InterruptedException, ExecutionException {
		
		List<Shop2> shops = Arrays.asList(new Shop2("Shop1"), new Shop2("Shop2"), new Shop2("Shop3"), new Shop2("Shop4"), new Shop2("Shop5"));
		//List<Shop3> shops = Arrays.asList(new Shop3("Shop1"), new Shop3("Shop2"), new Shop3("Shop3"), new Shop3("Shop4"), new Shop3("Shop5"));
		ExecutorService es = Executors.newFixedThreadPool(shops.size()*2);
		
		long start = System.nanoTime();
		
		Stream<CompletableFuture<String>> str = shops.stream()		//Mamy CompletableFuture<String>
			.map( shop -> CompletableFuture.supplyAsync( () -> shop.getPrice("Abc"), es))
			.map( cf -> cf.thenApply(Quote::parse))
			//.map( cf -> cf.thenApply(Discount::applyDiscount));
			.map( cf -> cf.thenCompose( quote -> CompletableFuture.supplyAsync( () -> Discount.applyDiscount(quote) ,es) ));
			
		CompletableFuture<?>[] a = str.map( fc -> fc.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
				.toArray(size -> new CompletableFuture[shops.size()]);
		
		CompletableFuture.allOf(a).join();	
			
		
		es.shutdown();
		
	}
    
	Callable<String> c = () -> { return "Arek"; };
	
	public String supMeth() {
		return "Arek"; 
	}
	
	
	public static void main(String[] args) throws Exception {
		new MyTest();
	}
	
}
