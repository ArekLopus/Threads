package threads.utils.executors.ForkJoinPool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ExecutorServiceVsForkJoinPool {

	long start = 0;
	ForkJoinPool fp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public ExecutorServiceVsForkJoinPool() throws InterruptedException, ExecutionException {
		
		Callable<String> c = () -> {
			try {
				Thread.sleep(300);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			System.out.println("Runnable - "+Thread.currentThread().getName()+", Executed in: "+((System.nanoTime()-start) / 1_000_000)+" ms");
			return "";
		};
		
		List<Callable<String>> list = Arrays.asList(c, c, c, c);
		
		start = System.nanoTime();
		fp.invokeAll(list);
		
		Thread.sleep(100);
		System.out.println("");
		
		start = System.nanoTime();
		es.invokeAll(list);
		
		//fp.awaitQuiescence(1000, TimeUnit.MILLISECONDS);
		es.shutdown();
	}
	
	
	public static void main(String[] args) throws Exception {
		new ExecutorServiceVsForkJoinPool();
	}
	
}
