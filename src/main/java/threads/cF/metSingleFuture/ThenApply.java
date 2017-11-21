package threads.cF.metSingleFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//<U> CF<U>	 thenApply(Function<? super T, ? extends U> fn) 			T -> U
//Returns a new CompletionStage that, when this stage completes normally,
//is executed with this stage's result as the argument to the supplied function.
public class ThenApply {

	public ThenApply() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cfs = CompletableFuture.supplyAsync(() -> {
			System.out.println("supplyAsync - "+Thread.currentThread().getName()+"");
			return "7744";
		}, es);
		
		CompletableFuture<Integer> cfi = cfs.thenApply( (str) -> Integer.parseInt(str) );	//Zamieniamy na Integer
		CompletableFuture<Double> cfd = cfi.thenApply( Double::new );						//Zamieniamy na Double
		CompletableFuture<Double> cfd2 = cfd.thenApplyAsync( Math::sqrt );						//Pierwiastek 2st z Double
		cfd2.thenAccept( s -> System.out.println(s+", "+Thread.currentThread().getName()) );
		
		System.out.println("\nPipeline");
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> { return "Arek"; })
			.thenApply( (s) -> s+" 1")
			.thenApply( (s) -> s+" 2")
			.thenApply( (s) -> s+" 3");
		System.out.println(cf.get());
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new ThenApply();
	}
	
}
