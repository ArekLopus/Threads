package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class AnErrorHandling {

	public AnErrorHandling() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool) throw new RuntimeException("ERROR!");
			
			System.out.println("CompletableFuture.runAsync(Runnable, Executor) - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		//#1 - exceptionally() 
		CompletableFuture<String> safe = cf.exceptionally( ex -> "We have a problem: "+((Throwable) ex).getMessage()+", "+Thread.currentThread().getName() );
		System.out.println("Supplied (exceptionally()): "+safe.get()+"\n");
		
		//#2 - handle()
		CompletableFuture<String> safe2 = cf.handle((el_is_ok, ex) -> {
		    if (el_is_ok != null) {
		        return el_is_ok+", "+Thread.currentThread().getName();
		    } else {
		        System.out.println("Problem :"+ ex);
		        return "Error"+", "+Thread.currentThread().getName();
		    }
		});
		System.out.println("Supplied (handle()): "+safe2.get()+"\n");
		
		//#3 - handleAsync()
		CompletableFuture<String> safe3 = cf.handleAsync((el_is_ok, ex) -> {
			if (el_is_ok != null) {
				return el_is_ok+", "+Thread.currentThread().getName();
			} else {
				System.out.println("Problem :"+ ex);
				return "Error"+", "+Thread.currentThread().getName();
			}
		});
		System.out.println("Supplied (handleAsync()): "+safe3.get()+"\n");
		
		//#4 - whenComplete()
		cf.whenComplete( (el_is_ok, ex) -> {
			if (el_is_ok != null) {
				System.out.println("Supplied (whenComplete()): "+el_is_ok+", "+Thread.currentThread().getName());
			} else {
				System.out.println("Problem :"+ ex);
				System.out.println("Supplied (whenComplete()): "+ex.getCause().getMessage()+", "+Thread.currentThread().getName());
			}
		});
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new AnErrorHandling();
	}
	
}
