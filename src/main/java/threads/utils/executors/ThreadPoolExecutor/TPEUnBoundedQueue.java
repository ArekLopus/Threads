package threads.utils.executors.ThreadPoolExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//Bounded / Unbounded queues 
public class TPEUnBoundedQueue {
	
	//ThreadPoolExecutor es = new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));	//Bounded
	ThreadPoolExecutor es = new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, new java.util.concurrent.LinkedBlockingQueue<Runnable>());	//Unbounded
	private int rejected = 0;
	
	public TPEUnBoundedQueue() throws InterruptedException {
		
		es.setRejectedExecutionHandler( (runnabe, executor) ->  {
			System.out.println("Rejected");
			rejected++;
		});
		
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
		new TPEUnBoundedQueue();
	}

}
