package threads.utils.ForkJoinPool.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkJoinPoolCustomization {

	public ForkJoinPoolCustomization() throws InterruptedException, ExecutionException {
		
		ForkJoinPool.ForkJoinWorkerThreadFactory wtf = new ForkJoinPool.ForkJoinWorkerThreadFactory() {
			private final AtomicInteger ai = new AtomicInteger();
			
			@Override
			public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
				ForkJoinWorkerThread wt = new ForkJoinWorkerThread(pool) {};
				Thread t = wt;
				t.setName("MyPool-1, Thread-"+(ai.getAndIncrement()+1));
				return wt;
			}
			
		};
		
		Thread.UncaughtExceptionHandler tuh = (t, e) -> { 
			System.out.println("t.setUncaughtExceptionHandler(): "+t.getName()+", Throwable1: "+e.getClass());
		};
		
		ForkJoinPool fp = new ForkJoinPool(4, wtf, tuh, true); 
		
		System.out.println(fp);
		System.out.println("Parallelism level: "+ForkJoinPool.getCommonPoolParallelism());
		
		fp.submit(r);
		fp.execute(c);
		
		
		fp.awaitQuiescence(1000, TimeUnit.MILLISECONDS);

	}
	
	Runnable r = () -> {
		try{
		Thread.sleep(50);
		} catch(Exception e) {
			
		}
		System.out.println("Runnable - "+Thread.currentThread().getName());
	};
	
	Runnable c = () -> {
		System.out.println("Callable - "+Thread.currentThread().getName());
		if(true) throw new RuntimeException("ERROR!");
	};
	
	public static void main(String[] args) throws Exception {
		new ForkJoinPoolCustomization();
	}
	
}


