package threads.utils.synchronizers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

//Any thread can release any number of permits, potentially increasing the number of permits beyond the initial count.
public class SemaphoreTest1 {
	
	private Semaphore sem = new Semaphore(5); 
	
	public SemaphoreTest1() throws InterruptedException, ExecutionException {
		
		sem.release();
		System.out.println("availablePermits: "+sem.availablePermits());
		sem.release();
		System.out.println("availablePermits: "+sem.availablePermits());
		
	}

	public static void main(String[] args) throws Exception {
		new SemaphoreTest1();
	}

}
