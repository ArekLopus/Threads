package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//exceptionally(Function<Throwable, ? extends T> fn) - Returns a new CF that is completed when this CF completes,
//with the result of the given function of the exception triggering this CF's completion when it completes exceptionally;
//otherwise, if this CF completes normally, then the returned CF also completes normally with the same value.
public class Exceptionally {

	public Exceptionally() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool) 
				throw new RuntimeException("ERROR!");
			System.out.println("supplyAsync(): "+Thread.currentThread().getName());
			return 12345;
		}, es);
		
		//CF zwracany przez exceptionally() MUSI być taki sam jak dany CF
		//CompletableFuture<String> chechIfExc = cf.exceptionally( ex -> "We have a problem: "+((Throwable) ex).getMessage()+", "+Thread.currentThread().getName() );
		CompletableFuture<Integer> chechIfExc = cf.exceptionally( ex -> {return -1;} );
		
		if(chechIfExc.get() < 0) {
			System.out.println("\n-----Exception was thrown!!!\n");
		} else {
			System.out.println("\nSupplied: "+chechIfExc.get()+"\n");
		}
		
		System.out.println("cf - isCompletedExceptionally?:\t\t"+cf.isCompletedExceptionally());
		System.out.println("chechIfExc -isCompletedExceptionally?:\t"+chechIfExc.isCompletedExceptionally());
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new Exceptionally();
	}
	
}
