package threads.utils.synchronizers;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierTest {
	
	private AtomicInteger sum = new AtomicInteger(0);
	private Runnable ba = () -> {						//The action can harvest the results of the individual threads.
		System.out.println("barrierAction, sum="+sum);	
	};
	private CyclicBarrier cb = new CyclicBarrier(3, ba);
	private ExecutorService es = Executors.newCachedThreadPool();
	
	public CyclicBarrierTest() throws InterruptedException {
		
		Callable<Integer> c = () -> {
			int time = (ThreadLocalRandom.current().nextInt(10)+1)*100;
			System.out.println("Sleeping: "+time+", "+Thread.currentThread().getName());
			Thread.sleep(time);
			sum.addAndGet(time);
			cb.await();
			System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
			return time;
		};
		
		for(int i=0; i<3; i++) {
			es.submit(c);
		}
		
		Thread.sleep(3000);
		System.out.println("---Second cycle---");
		cb.reset();		//The barrier is called cyclic because it can be reused after all waiting threads been released. 
		for(int i=0; i<3; i++) {
			es.submit(c);
		}
		
		es.shutdown();
	}

	public static void main(String[] args) throws Exception {
		new CyclicBarrierTest();
	}
}