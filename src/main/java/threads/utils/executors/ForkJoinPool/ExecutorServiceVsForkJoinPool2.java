package threads.utils.executors.ForkJoinPool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ExecutorServiceVsForkJoinPool2 {

	long start = 0;
	ForkJoinPool fp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public ExecutorServiceVsForkJoinPool2() throws InterruptedException, ExecutionException {
		
		Callable<String> c = () -> {
			sumIt(1000);
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
	
	private int sumIt(int num) {
		long sum = 0;
		long val = 50_000L * num;
		for (int i=0; i< val; i++) {
			sum = sum + i; 
		}
		return num;
	}
	
	public static void main(String[] args) throws Exception {
		new ExecutorServiceVsForkJoinPool2();
	}
	
}
