package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//Blokujemy watek (cf.get()), a potem go odblokowujemy cf.complete("Abc");
//CompletableFuture.complete() can only be called once, subsequent invocations are ignored.
public class Complete {
	
	public Complete() throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> cf = waitToUnblock();
		
		Runnable callback = () -> {
			try {
				System.out.println("Value: "+cf.get()+", "+Thread.currentThread().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		Thread.sleep(300);
		CompletableFuture.runAsync(callback);
		cf.complete("Abc");
		
		Thread.sleep(600);
		new Thread(callback).start();
		
		//CompletableFuture.complete() can only be called once, subsequent invocations are ignored. 
		cf.complete("Abc1");
		
	}
	
	
	public CompletableFuture<String> waitToUnblock() {
	    CompletableFuture<String> future = new CompletableFuture<>();
	    return future;
	}


	
	
	public static void main(String[] args) throws Exception {
		new Complete();
	}
	
}
