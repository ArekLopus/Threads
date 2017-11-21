package thread.reenterantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	
	private int counter = 1;
	
	private Lock myLock = new ReentrantLock();
	
	public ReentrantLockTest() throws InterruptedException {
		
		System.out.println("Without Lock:");
		withoutLock();
		Thread.sleep(500);
		System.out.println("\nWith Lock:");
		counter = 1;
		withLock();
		
	}
	
	private void withoutLock() {
		Runnable r = () -> {
			int c = getCounter();
			c++;
			System.out.print(counter+", ");
			setCounter(c);
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	private void withLock() {
		Runnable r = () -> {
			myLock.lock(); 			// a ReentrantLock object
			try {	    
				int c = getCounter();
				c++;
				System.out.print(counter+", ");
				setCounter(c);
			} finally {
				myLock.unlock(); 	// make sure the lock is unlocked even if an exception is thrown
			}
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new ReentrantLockTest();
	}
	
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
