package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//Jak damy isCompletedExceptionally? zaraz za CF to się wykonuje szybciej niz dane CF i wyświtla false przy wyjątku 
public class Exceptionally2 {

	public Exceptionally2() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool) 
				throw new RuntimeException("ERROR!");
			System.out.println("supplyAsync(): "+Thread.currentThread().getName());
			return 12345;
		}, es);
		
		CompletableFuture.supplyAsync( () -> {
			System.out.println("(1)cf - isCompletedExceptionally?:\t\t"+cf.isCompletedExceptionally());
			return null; 
		});
		
		//Thread.sleep(100);	 
		//System.out.println("cf - isCompletedExceptionally?:\t\t"+cf.isCompletedExceptionally());
		
		CompletableFuture<Integer> chechIfExc = cf.exceptionally( ex -> {return -1;} );

		if(chechIfExc.get() < 0) {
			System.out.println("\n-----Exception was thrown!!!\n");
		} else {
			System.out.println("\nSupplied: "+chechIfExc.get()+"\n");
		}
		
		System.out.println("(2)cf - isCompletedExceptionally?:\t\t"+cf.isCompletedExceptionally());
		System.out.println("chechIfExc -isCompletedExceptionally?:\t"+chechIfExc.isCompletedExceptionally());
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new Exceptionally2();
	}
	
}
