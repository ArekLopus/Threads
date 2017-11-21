package threads.utils.ForkJoinPool.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolCustomization2 {

	public ForkJoinPoolCustomization2() throws InterruptedException, ExecutionException {
		
		ForkJoinPool fp = new ForkJoinPool(); 
		
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
		new ForkJoinPoolCustomization2();
	}
	
}


