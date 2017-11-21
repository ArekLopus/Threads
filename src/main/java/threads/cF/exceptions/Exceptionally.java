package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//exceptionally( Throwable -> T ) - Returns a new CF that is completed when this CF completes,
//Jeśli dany CF zakończy się wyjątkiem to nowy CF wykona dostarczoną Funkcję
//Jeśli dany CF wykona się poprawnie to nowy CF zwróci wartość zwracaną przez dany CF
//MUST USE ANOTHER CompletableFuture to handle Exception, if you use the main CF it always throws Exception in your face.
//Typ CF zwracany przez exceptionally() MUSI być taki sam jak dany CF
public class Exceptionally {

	public Exceptionally() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool) 
				throw new RuntimeException("ERROR!");
			return 12345;
		}, es);
		
		//Typ CF zwracany przez exceptionally() MUSI być taki sam jak dany CF
		//CompletableFuture<String> chechIfExc = cf.exceptionally( ex -> "We have a problem: "+((Throwable) ex).getMessage()+", "+Thread.currentThread().getName() );
		CompletableFuture<Integer> fixed = cf.exceptionally( ex -> { return -1; } );
		
		if(fixed.get() < 0) {
			System.out.println("-----Exception was thrown!!! Returns: "+fixed.get()+"\n");
		} else {
			System.out.println("Supplied: "+fixed.get()+"\n");
		}
		
		System.out.println("cf - isCompletedExceptionally?:\t\t"+cf.isCompletedExceptionally());
		System.out.println("fixed - isCompletedExceptionally?:\t"+fixed.isCompletedExceptionally());
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new Exceptionally();
	}
	
}
