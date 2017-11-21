package threads.utils.executors.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//ThreadPoolExecutor.class  extends AbstractExecutorService. This class provides many adjustable parameters and extensibility hooks. 
public class ThreadPoolExecutorTest {
	
	//ExecutorService es = new ThreadPoolExecutor(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
	//ThreadPoolExecutor es = new ThreadPoolExecutor(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
	ThreadPoolExecutor es = new ThreadPoolExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
	private int rejected = 0;
	
	public ThreadPoolExecutorTest() throws InterruptedException {
		es.prestartAllCoreThreads();
		
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
		new ThreadPoolExecutorTest();
	}

}
