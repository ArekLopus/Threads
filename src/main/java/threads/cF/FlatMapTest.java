package threads.cF;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class FlatMapTest {

	public FlatMapTest() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		CompletableFuture<String> cf = CompletableFuture.supplyAsync( () -> {
			return "Arek"; 
		}  , es); 
		

		System.out.println(cf.get());
		
		List<Integer> l1 = Arrays.asList(1,2,3);
		List<Integer> l2 = Arrays.asList(4,5,6);
		
		Stream.of(l1, l2).flatMap( l -> l.stream() ).forEach(System.out::println);
		
		
		List<Integer> l3 = Arrays.asList(1);
		List<Integer> l4 = Arrays.asList(2);
		
		Stream.of(l3, l4).flatMap( l -> l.stream() ).forEach(System.out::println);
		
		es.shutdown();
		
	}
    
	
	public static void main(String[] args) throws Exception {
		new FlatMapTest();
	}
	
}
