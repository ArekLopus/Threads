package threads.utils.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsScheduledTest {
	
	ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);		//zw ScheduledhreadPoolExecutor
	//ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();	//zw Executors.DelegatedScheduledExecutorService  exts DelegatedExecutorService  impls ScheduledExecutorService
	
	public ExecutorsScheduledTest() throws InterruptedException, ExecutionException {
		
		Future<String> f = ses.submit(c);
		System.out.println("Returned: "+f.get());
		ses.schedule(c, 100, TimeUnit.MILLISECONDS);
		
		ses.scheduleAtFixedRate(r, 300, 1000, TimeUnit.MILLISECONDS);
		ses.scheduleWithFixedDelay(r2, 400, 1000, TimeUnit.MILLISECONDS);
		
		Thread.sleep(4000);
		
		ScheduledThreadPoolExecutor stpe = (ScheduledThreadPoolExecutor) ses;
		System.out.println("Pool size: "+stpe.getPoolSize()); 
		System.out.println("Callable class: "+c.getClass().getName());
		
		ses.shutdown();
	}
	
	
	public static void main(String[] args) throws Exception {
		new ExecutorsScheduledTest();
	}
	
	Callable<String> c = () -> {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Callable c - Thread: "+Thread.currentThread().getName());
		return "From Callable";
	};
	Runnable r = () -> {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Runnable r1 - Thread: "+Thread.currentThread().getName());
	};
	Runnable r2 = () -> {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Runnable r2 - Thread: "+Thread.currentThread().getName());
	};
}
