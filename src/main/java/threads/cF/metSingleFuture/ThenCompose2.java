package threads.cF.metSingleFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//<U> CF<U>	 thenCompose(Function<? super T,  ? extends CompletionStage<U>> fn) 	T -> CF<U>
//Returns a new CompletionStage that, when this stage completes normally, is executed with this stage as the argument to the supplied function.
//Used to chain one future dependent on the other, it is an essential method to building robust,
//asynchronous pipelines, without blocking or waiting for intermediate steps.
public class ThenCompose2 {

	public ThenCompose2() throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> cfs = CompletableFuture.supplyAsync(this::getMeStg);
		
		CompletableFuture<Integer> intFuture = cfs.thenCompose(this::calculateInt);
		
		System.out.println("thenCompose:\t"+intFuture.get());
		System.out.println(intFuture.get().getClass());
		
		
		//The same using thenApply()
		CompletableFuture<CompletableFuture<Integer>> f = cfs.thenApply(this::calculateInt);
		System.out.println("thenApply:\t"+f.get().get());
	}
	
	private CompletableFuture<Integer> calculateInt(String str) {
		return CompletableFuture.supplyAsync( () -> { 
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Integer.parseInt(str);
		});
	}
	
	private String getMeStg() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "12";
	}
	
	public static void main(String[] args) throws Exception {
		new ThenCompose2();
	}
	
}
