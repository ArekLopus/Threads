package threads.cF.basic;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//obtrudeException(Throwable ex) - Forcibly causes subsequent invocations of 
//get() and related methods to throw the given exception, whether or not already completed. 
public class ObtrudeException {

	public ObtrudeException() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		CompletableFuture<Integer> cf = CompletableFuture.supplyAsync( () -> {
			//Bez znaczenia co tu jest, zawsze rzuca Exception
			return 12345;
		}, es);
		
		cf.obtrudeException( new RuntimeException("Bo Tak!") );
		
		System.out.println("Consumed: "+cf.get());			//ExecutionException
		//System.out.println("Consumed: "+cf.join());		//CompletionException
		
		es.shutdown();
		
	}
	
	public static void main(String[] args) throws Exception {
		new ObtrudeException();
	}
	
}
