package threads.utils.ForkJoinTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;

	//Invoking a Runnable/Callable using ForkJoinTask
//Method invoke() is semantically equivalent to fork(); join() but always attempts to begin execution in the current thread.
//ForkJoinTask provides adapt methods for Runnable and Callable, that may be of use when mixing execution of ForkJoinTasks with other kinds of tasks.
//When all tasks are of this form, consider using a pool constructed in asyncMode.
public class ForkJoinTaskAdopt {
	
	Runnable r = () -> { System.out.println("Runnable called! - "+Thread.currentThread().getName()); };
	Callable<String> c = () -> { return "Callable called! - "+Thread.currentThread().getName(); };
	
	public ForkJoinTaskAdopt() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();

		//ForkJoinTask<?> t1 = ForkJoinTask.adapt(r, "AbC");
		ForkJoinTask<String> t1 = ForkJoinTask.adapt(c);
		
		System.out.println("--"+t1.invoke());			//uses current thread, returns value
		//System.out.println("--"+t1.fork().join());	//uses ForkJoinPool.commonPool-worker-1, returns Future
		
		System.out.println("isCompletedNormally(): "+t1.isCompletedNormally());
		System.out.println("isCompletedAbnormally(): "+t1.isCompletedAbnormally());
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		
		Thread.sleep(100);
		
	}
	
	public static void main(String[] args) throws Exception {
		new ForkJoinTaskAdopt();
	}

}