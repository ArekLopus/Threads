package threads.utils.synchronizers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class SemaphoreTest2 {
	
	private int loops = 1;
	private Semaphore sem = new Semaphore(5); 
	
	public SemaphoreTest2() throws InterruptedException, ExecutionException {
		
		for (int i=0; i<loops; i++) {
			sem.acquireUninterruptibly();
			Thread.currentThread().interrupt();
			System.out.println("availablePermits: "+sem.availablePermits());
			sem.release();
			System.out.println("availablePermits: "+sem.availablePermits());
		}
			
	}

	Callable<Integer> c = () -> {
		int time = (ThreadLocalRandom.current().nextInt(10)+1)*100;
		System.out.println("Time: "+time+", thread: "+Thread.currentThread().getName());
		Thread.sleep(time);
		return time;
	};
	
	
	public static void main(String[] args) throws Exception {
		new SemaphoreTest2();
	}

}
