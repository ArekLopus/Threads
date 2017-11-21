package threads.cF.metSingleFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//<U> CF<U>	 thenCompose(Function<? super T,  ? extends CompletionStage<U>> fn) 	T -> CF<U>
//Returns a new CompletionStage that, when this stage completes normally, is executed with this stage as the argument to the supplied function.
//Used to chain one future dependent on the other, it is an essential method to building robust,
//asynchronous pipelines, without blocking or waiting for intermediate steps.
public class ThenCompose {
	
	private ExecutorService es = Executors.newFixedThreadPool(4);
	
	public ThenCompose() throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> cfs = CompletableFuture.supplyAsync( () -> { return "12345"; }, es );
		//tworzy inny CF (cfi) na bazie wyniku z pierwszego (cfs)    
		CompletableFuture<Integer> cfi = cfs.thenComposeAsync( this::parseIt, es); 
		
		System.out.println("thenCompose:\t"+cfi.get()+", "+Thread.currentThread().getName()+", "+cfi.get().getClass());

		
		//To samo uzywając thenApply() - łatwiej thenCompose(), nie tworzy skomplikowanej struktury
		System.out.println();
		CompletableFuture<CompletableFuture<Integer>> f = cfs.thenApplyAsync( this::parseIt );
		System.out.println("thenApply:\t"+f.get().get()+", "+Thread.currentThread().getName()+", "+cfi.get().getClass());
		
		es.shutdown();
	}
	
	
	public static void main(String[] args) throws Exception {
		new ThenCompose();
	}
	
	private CompletableFuture<Integer> parseIt(String str) {
		return CompletableFuture.supplyAsync( () -> {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Integer parsed = Integer.parseInt(str);
			System.out.println("parsed by: "+Thread.currentThread().getName()); 
			return parsed;
		}, es);
	}
}
