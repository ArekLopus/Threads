package threads.cF;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StreamAndCF {

	public StreamAndCF() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		List<Integer> list = Arrays.asList(10, 20, 30, 40);
        
		list.stream()
			.map( data -> CompletableFuture.supplyAsync( () -> getNumber(data)) )	//Number cov to CF - 10*10
			.map( compFuture -> compFuture.thenApply(n -> n*n) )					//function applied - 100*100
			.map( t -> t.join() )													//Zwraca watrość - 10000
            .forEach( s -> System.out.println(s) );									//Dla kazdego wyświetla
		
		es.shutdown();
		
	}
    
	private static int getNumber(int a){
        return a*a;
    }
    
	public static void main(String[] args) throws Exception {
		new StreamAndCF();
	}
	
}
