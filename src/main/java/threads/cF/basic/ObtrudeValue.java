package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//obtrudeValue(T value) - Forcibly sets or resets the value subsequently returned by method get() and related methods, whether or not already completed.
//obtrudeValue(val) overrides previous value of the Future with new one. Use with caution. 
public class ObtrudeValue {
	
	public ObtrudeValue() throws InterruptedException, ExecutionException {
		
		CompletableFuture<String> cf = waitingCF();
		
		Runnable r = () -> {
			try {
				System.out.println("Value: "+cf.get()+", "+Thread.currentThread().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		CompletableFuture.runAsync(r);
		Thread.sleep(200);
		cf.complete("Abc");
		
		Thread.sleep(200);
		new Thread(r).start();
		cf.obtrudeValue("NewAbc");
		
		Thread.sleep(200);
		new Thread(r).start();
		cf.complete("Abc1");
		
	}
	
	
	public CompletableFuture<String> waitingCF() {
	    CompletableFuture<String> future = new CompletableFuture<>();
	    return future;
	}


	
	
	public static void main(String[] args) throws Exception {
		new ObtrudeValue();
	}
	
}
