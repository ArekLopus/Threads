package threads.utils.ForkJoinPool.pool;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolCommonPool2 {

	public ForkJoinPoolCommonPool2() throws InterruptedException, ExecutionException {
//However this pool and any ongoing processing are automatically terminated upon program System.exit(int).
//Any program that relies on asynchronous task processing to complete before program termination should invoke commonPool().awaitQuiescence, before exit. 
		
		ForkJoinPool fp = ForkJoinPool.commonPool();
		
		fp.execute(r);
		fp.submit(c);
		fp.submit(r);
		fp.invokeAll(Arrays.asList(c));
		fp.invokeAny(Arrays.asList(c));
		
		fp.awaitQuiescence(1000, TimeUnit.MILLISECONDS);
		//ForkJoinPool.commonPool().awaitQuiescence(1000, TimeUnit.MILLISECONDS);
		//Thread.sleep(100);
	}
	
	Runnable r = () -> {
		System.out.println("Runnable - "+Thread.currentThread().getName());
	};
	
	Callable<String> c = () -> {
		System.out.println("Callable - "+Thread.currentThread().getName());
		return null;
	};
	
	public static void main(String[] args) throws Exception {
		new ForkJoinPoolCommonPool2();
	}
	
}
