package threads.cF.startComputation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

//You don’t explicitly start the computation.The supplyAsync() starts it automatically,
//the other methods cause it to be continued.
public class SupplyAsync {

	public SupplyAsync() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		Supplier<String> sup = this::getMeStg;
		
		CompletableFuture.supplyAsync(sup , es);
		CompletableFuture.supplyAsync( () -> getMeStg() );
		CompletableFuture.supplyAsync(sup , es);
		
		System.out.println("If you dont block with get(), this is async");
		
		es.shutdown();
	}
	
	public String getMeStg() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("supplyAsync() - "+Thread.currentThread().getName());
		return "Arek";
	}
	
	public static void main(String[] args) throws Exception {
		new SupplyAsync();
	}
	
}
