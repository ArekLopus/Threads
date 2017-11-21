package threads.cF.metSingleFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//<U> CF<U>  handle(BiFunction<? super T,  Throwable,  ? extends U> fn) 		(T, Throwable) -> U
///Returns a new CompletionStage that, when this stage completes either normally or exceptionally,
//is executed with this stage's result and exception as arguments to the supplied function.
public class Handle {

	public Handle() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!");
			System.out.println("supplyAsync() - "+Thread.currentThread().getName());
			return 12345;
		}, es);

		cf.handle((el, ex) -> {
		    if (el != null) {
		        return el+", "+Thread.currentThread().getName();
		    } else {
		        System.out.println("cf.handle: The Problem is: "+ ex+", "+Thread.currentThread().getName());
		        return "Error"+", "+Thread.currentThread().getName();
		    }
		});
		
		CompletableFuture<String> cf1 = cf.handleAsync((el, ex) -> {
			if (el != null) {
				return el+", "+Thread.currentThread().getName();
			} else {
				System.out.println("cf1.handleAsync: The Problem is: "+ ex+", "+Thread.currentThread().getName());
				return "Error"+", "+Thread.currentThread().getName();
			}
		});
		System.out.println("CompletableFuture<String> cf1.get(): "+cf1.get().getClass());
		System.out.println("CompletableFuture<String> cf1.get(): "+cf1.get());
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new Handle();
	}
	
}
