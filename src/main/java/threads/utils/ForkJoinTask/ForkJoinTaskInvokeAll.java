package threads.utils.ForkJoinTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;

//ForkJoinTask.invoke() is semantically equivalent to fork(); join() but always attempts to begin execution in the current thread.
public class ForkJoinTaskInvokeAll {
	
	Runnable r = () -> { System.out.println("Runnable called! - "+Thread.currentThread().getName()); };
	Callable<String> c = () -> {
		Thread.sleep(1000);
		return "Callable called! - "+Thread.currentThread().getName(); 
	};
	
	public ForkJoinTaskInvokeAll() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();

		//ForkJoinTask<?> t1 = ForkJoinTask.adapt(r, "AbC");
		ForkJoinTask<String> t1 = ForkJoinTask.adapt(c);
		ForkJoinTask<String> t2 = ForkJoinTask.adapt(c);
		
		//System.out.println("--"+t1.invoke());
		//System.out.println("--"+t2.invoke());	//invokes sequentially!!!!
		
		ForkJoinTask.invokeAll(t1, t2);			//invokes in parallel!
		System.out.println("--"+t1.join());
		System.out.println("--"+t2.join());
		
		System.out.println("isCompletedNormally(): "+t1.isCompletedNormally());
		System.out.println("isCompletedAbnormally(): "+t1.isCompletedAbnormally());
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		
		Thread.sleep(100);
		
	}
	
	public static void main(String[] args) throws Exception {
		new ForkJoinTaskInvokeAll();
	}

}