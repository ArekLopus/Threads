package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//public T join() - Returns the result value when complete, or throws an (unchecked) exception if completed exceptionally.  
public class Join {

	public Join() {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool)
				throw new RuntimeException("ERROR!");
			try {
				Thread.sleep(400);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Arek"; 
		}, es); 
		
		CompletableFuture<String> chechIfExc = cf.exceptionally( ex -> "We have a problem: "+((Throwable) ex).getMessage()+", "+Thread.currentThread().getName() );
		
		System.out.println("Unlike get(), join() (the method itself) does not throw any Exceptions. It throws Exception if completed exceptionally"
				+ "\njoin() returns: "+chechIfExc.join());
		System.out.println("isCompletedExceptionally?: "+cf.isCompletedExceptionally());
		
		es.shutdown();
	}
    
	
	public static void main(String[] args) throws Exception {
		new Join();
	}
	
}
