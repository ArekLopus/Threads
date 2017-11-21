package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//CompletableFuture.getNow(valueIfAbsent) doesn't block but if the Future is not completed yet, returns default value
public class GetNow {

	public GetNow() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			try {
				Thread.sleep(400);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Arek"; 
		}, es); 
		
		System.out.println("Because method has not completed, CF returns this: "+cf.getNow("NOT comleted!"));
		System.out.println("isCompletedExceptionally?: "+cf.isCompletedExceptionally());
		
		System.out.println(cf.get());
		
		es.shutdown();
		//ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
	}
    
	
	public static void main(String[] args) throws Exception {
		new GetNow();
	}
	
}
