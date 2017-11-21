package threads.cF.exceptions;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

//If not already completed, invocations of get() and related methods causes to throw the given exception.
public class CompleteExceptionally {

	public CompleteExceptionally() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			boolean bool = ThreadLocalRandom.current().nextBoolean();
			if(bool) throw new RuntimeException("ERROR!");
			
			System.out.println("supplyAsync() - "+Thread.currentThread().getName());
			return "Arek";
		}, es);
		
		//Jak nie zdązy się wykonac to co w cf to get() rzuci wyjątek z completeExceptionally(ex)
		//Thread.sleep(100);
		cf.completeExceptionally(new TooLateExceprion("Trochę za późno!"));
		
		try {
			System.out.println("Supplied: "+cf.get()+"\n");
		} catch (Exception ex) {
			System.out.println("Exeption thrown: "+ex.getMessage()+"\n");
		}
		
		es.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new CompleteExceptionally();
	}
	
	class TooLateExceprion extends Exception {
		private static final long serialVersionUID = 1L;
		
		public TooLateExceprion() {
			super();
		}
		public TooLateExceprion(String str) {
			super(str);
		}
	}
}
