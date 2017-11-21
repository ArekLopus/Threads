package threads.utils.ThreadFactoryCustom;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadFactory {
	
	private AtomicInteger threadCounter = new AtomicInteger(1);
    //ThreadFactory intf -> Thread newThread(Runnable r);
    private final Executor tf = Executors.newCachedThreadPool( runnable -> {
        Thread th = Executors.defaultThreadFactory().newThread(runnable);
        th.setName("MyThread-"+threadCounter.getAndIncrement());
		th.setPriority(Thread.MAX_PRIORITY);
        th.setDaemon(true);
        ;        
        return th;
    });
	
	public SimpleThreadFactory() {
		
		Runnable r = ()->{
			System.out.println("Runnable, thread: "+Thread.currentThread().getName());
		};
		
		tf.execute(r);
		tf.execute(r);
		tf.execute(r);
		
	}

	public static void main(String[] args) {
		new SimpleThreadFactory();
	}

}
