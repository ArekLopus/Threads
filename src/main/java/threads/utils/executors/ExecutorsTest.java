package threads.utils.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorsTest {
	
	ExecutorService es = Executors.newCachedThreadPool();
	//ExecutorService es = Executors.newFixedThreadPool(2);
	//ExecutorService es = Executors.newSingleThreadExecutor();
	
	public ExecutorsTest() throws InterruptedException, ExecutionException {
		
		Future<String> f = es.submit(c);
		es.submit(c);
		es.submit(c);
		System.out.println("Returned: "+f.get());
		
		Thread.sleep(500);
		ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
		System.out.println("Pool size: "+tpe.getPoolSize()); 
		
		es.shutdown();
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new ExecutorsTest();
	}
	
	Callable<String> c = () -> {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Thread: "+Thread.currentThread().getName());
		return "Abc";
	};
}
