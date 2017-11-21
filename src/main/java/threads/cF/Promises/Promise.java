package threads.cF.Promises;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//CF który kończymy wynikiem albo wyjątkiem i zwracamy
public class Promise {

	public Promise() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = promiseExample();
		//Wheb we got a resulting CF we can return that Future as method return value, apply a function on top of it,
		//handle the Exception, compose it with something different – fe use timeout method from the other example. IT IS EXTREMELY, EXTREMELY USEFUL!
		
		CompletableFuture<String> fixed = cf.exceptionally( ex -> ex.getMessage() );
		System.out.println(fixed.get());
		
		CompletableFuture<String> applied = fixed.thenApply( (str) -> str + "  ---We can apply another operation here" );
		applied.thenAccept(System.out::println);
		
		
		es.shutdown();
		
	}
    
	private CompletableFuture<String> promiseExample() {
		CompletableFuture<String> promise = new CompletableFuture<>();
		//Some computation here -> if it returns a value - complet(), if returns Exception - completeExceptionally()
		boolean result = ThreadLocalRandom.current().nextBoolean();
		if(result == true) {
			promise.complete("Operation Succesful!!!");
		} else {
			promise.completeExceptionally( new RuntimeException("---Operation Failed!") );
		}
		
		return promise;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		new Promise();
	}
	
}
