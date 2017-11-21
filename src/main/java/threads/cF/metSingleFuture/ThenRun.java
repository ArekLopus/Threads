package threads.cF.metSingleFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//CF<Void>	thenRun(Runnable action) 				() -> void
//Returns a new CompletionStage that, when this stage completes normally, executes the given action.
public class ThenRun {

	public ThenRun() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		CompletableFuture<String> cfs = CompletableFuture.supplyAsync( () -> { return "Arek"; } );
		
		cfs.thenRun(r);
		Thread.sleep(120);
		cfs.thenRunAsync(r);
		Thread.sleep(120);
		cfs.thenRunAsync(r, es);
		Thread.sleep(20);
		
		es.shutdown();
	}
	
	Runnable r = () -> {
		System.out.println("Runnable\t- "+Thread.currentThread().getName());
	};
	
	public static void main(String[] args) throws Exception {
		new ThenRun();
	}
	
}
