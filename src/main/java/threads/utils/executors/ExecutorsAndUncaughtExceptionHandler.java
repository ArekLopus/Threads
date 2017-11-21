package threads.utils.executors;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsAndUncaughtExceptionHandler {
//http://stackoverflow.com/questions/1838923/why-is-uncaughtexceptionhandler-not-called-by-executorservice
//The exceptions which are thrown by tasks which where submitted to an ExecutorService through the submit() method get wrapped into an ExcecutionException
//and are rethrown by the Future.get() method. This is, because the executor considers the exception as part of the result of the task.
//If you however submit a task via the execute() method which originates from the Executor interface, the UncaughtExceptionHandler is notified.
	
	public ExecutorsAndUncaughtExceptionHandler() throws Exception {
		CatchExceptionsThreadFactory tf = new CatchExceptionsThreadFactory();
		ExecutorService es = Executors.newCachedThreadPool(tf);
		//ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;

		es.submit(c1);
		es.submit(c1);
		
		Thread.sleep(20);
		
		//es.submit(c2);      //does not work directly
		Future<?> submit = es.submit(c2);
		es.execute(c2);     //Working
		
		Thread.sleep(150);
		
		es.shutdown();
		
		Thread.setDefaultUncaughtExceptionHandler(CatchExceptionsThreadFactory.handler);
		submit.get();
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new ExecutorsAndUncaughtExceptionHandler();
	}
	
	Callable<String> c1 = () -> {
		System.out.println("c1, Thread: "+Thread.currentThread().getName());
		return "Abc";
	};
	
	Runnable c2 = () -> {
		System.out.println("c2, Thread: "+Thread.currentThread().getName());
		if(true) throw new RuntimeException("ERROR!");
	};
}

class CatchExceptionsThreadFactory implements ThreadFactory {
	private final AtomicInteger ai = new AtomicInteger();
	
	public static UncaughtExceptionHandler handler = (t, e) -> { 
		System.out.println("UncaughtExceptionHandler: "+t.getName()+", Throwable: "+e.getClass());
	};
	
    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r);
        th.setName("pool-1, Thread-"+(ai.getAndIncrement()+1));
        th.setUncaughtExceptionHandler(CatchExceptionsThreadFactory.handler);
        return th;
    }
}
