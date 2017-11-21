package threads.cF.startComputation;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class SupplyAsync2 {

	public SupplyAsync2() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		Callable<String> c1 = () -> {
			System.out.println("Callable<String> - "+Thread.currentThread().getName());
			return "Arek"; 
		};
		Supplier<String> sup = () -> { 
			System.out.println("Supplier<String> sup called, "+Thread.currentThread().getName());
			return "Arek";
		};
		Supplier<String> sup2 = () -> { 
			try {
				System.out.println("Supplier<String> sup2 calling Callable.call() - "+Thread.currentThread().getName());
				return c1.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		};
		
		CompletableFuture.supplyAsync(sup);
		CompletableFuture.supplyAsync(this::supplierMeth);
		
		Thread.sleep(200);
		//CompletableFuture.supplyAsync(c1);
		System.out.println("\nYou cannot supply Callable directly");
		CompletableFuture.supplyAsync(sup2, es);

		es.shutdown();
	}
    
	public String supplierMeth() {
		System.out.println("supplierMeth() called, "+Thread.currentThread().getName());
		return "Arek"; 
	}
	
	
	public static void main(String[] args) throws Exception {
		new SupplyAsync2();
	}
	
}
