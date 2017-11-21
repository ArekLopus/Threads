package threads.cF.metSingleFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

//CF<Void>	thenAccept(Consumer<? super T> action) 			T -> void
//Returns a new CompletionStage that, when this stage completes normally,
//is executed with this stage's result as the argument to the supplied action.
public class ThenAccept {
	
	int counter = 1000;
	
	public ThenAccept() throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		Supplier<String> sup = this::getMeStg;
		Consumer<String> con = this::useMess;
		
		CompletableFuture.supplyAsync(sup).thenAccept(con);
		CompletableFuture.supplyAsync(sup , es).thenAccept(con);
		
		CompletableFuture.supplyAsync(sup).thenAcceptAsync(con);
		CompletableFuture.supplyAsync(sup , es).thenAcceptAsync(con);
		CompletableFuture.supplyAsync(sup , es).thenAcceptAsync(con, es);
		
		System.out.println("CompletableFuture is here totally async");
		
		es.shutdown();
	}
	
	public String getMeStg() {
		try {
			Thread.sleep(counter = counter+200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print("Supplier<String> - "+Thread.currentThread().getName()+"\t\t");
		return "Arek";
	}
	public void useMess(String str) {
		System.out.println("Consumer<String> - "+Thread.currentThread().getName()+" ---Consumed: "+str);
	}
	
	
	public static void main(String[] args) throws Exception {
		new ThenAccept();
	}
	
}
