package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//isDone() - Returns true if completed in any fashion: normally, exceptionally, or via cancellation. 
public class IsDone {
	
	int sleep = (ThreadLocalRandom.current().nextInt(2))*100;
	
	public IsDone() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			try {
				boolean bool = ThreadLocalRandom.current().nextBoolean();
				if(bool)
					throw new Exception("ERROR!");
				Thread.sleep(50);
			} catch (Exception e) {
				return "--- Finished Exceptionally: "+e.getMessage();
			}
			return "Arek"; 
		}, es); 
		
		
		System.out.println("Sleep: "+sleep);
		Thread.sleep(sleep);
		
		if(cf.isDone()) {
			System.out.println("Done, got: "+cf.get());
		} else {
			System.out.println("NOT Done!");
		}
		
		es.shutdown();
	}
    
	
	public static void main(String[] args) throws Exception {
		new IsDone();
	}
	
}
