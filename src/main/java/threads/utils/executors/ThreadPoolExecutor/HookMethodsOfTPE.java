package threads.utils.executors.ThreadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//Hook methods 
//This class provides protected overridable beforeExecute(java.lang.Thread, java.lang.Runnable) and afterExecute(java.lang.Runnable, java.lang.Throwable) methods
//that are called before and after execution of each task. These can be used to manipulate the execution environment; 
//fe, reinitializing ThreadLocals, gathering statistics, or adding log entries.
//Additionally, method terminated() can be overridden to perform any special processing that needs to be done once the Executor has fully terminated. 
//If hook or callback methods throw exceptions, internal worker threads may in turn fail and abruptly terminate.
public class HookMethodsOfTPE extends ThreadPoolExecutor {
	
	public HookMethodsOfTPE(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		System.out.println("Before Execution "+t.getName());
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		System.out.println("After Execution, class: "+r.getClass().getSimpleName());
	}

	@Override
	protected void terminated() {
		super.terminated();
		System.out.println("-=Terminated!=-");
	}

	public static void main(String[] args) throws Exception {
		
		ThreadPoolExecutor es = new HookMethodsOfTPE(5, 5, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
		Runnable r = () -> {
			System.out.println("--Runnable - "+Thread.currentThread().getName());	
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		es.setRejectedExecutionHandler( (runnabe, executor) ->  {
			System.out.println("Rejected");
		});
		es.prestartAllCoreThreads();
		
		for(int i=0; i<5; i++) {
			es.submit(r);
		}		
		
		Thread.sleep(100);
		System.out.println(es);
		es.shutdown();
		System.out.println(es);

	}

}
