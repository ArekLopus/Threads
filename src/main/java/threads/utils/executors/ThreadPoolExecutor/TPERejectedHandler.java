package threads.utils.executors.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//ThreadPoolExecutor.class  extends AbstractExecutorService. This class provides many adjustable parameters and extensibility hooks. 
public class TPERejectedHandler {
	
	ThreadPoolExecutor es = new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
	private int rejected = 0;
	
	public TPERejectedHandler() throws InterruptedException {
		es.prestartAllCoreThreads();
		
		//es.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		es.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		//es.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
		//es.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		
//		es.setRejectedExecutionHandler( (runnabe, executor) ->  {
//			System.out.println("Rejected");
//			rejected++;
//		});
		
		
		for(int i=0; i<100; i++) {
			es.submit(r);
		}		
		
		Thread.sleep(100);
		System.out.println(es);
		es.shutdown();
		System.out.println(es);
		System.out.println("Rejected: "+rejected);
	}
	
	Runnable r = () -> {
		System.out.println("Abc - "+Thread.currentThread().getName());
	};
	
	
	public static void main(String[] args) throws Exception {
		new TPERejectedHandler();
	}

}
