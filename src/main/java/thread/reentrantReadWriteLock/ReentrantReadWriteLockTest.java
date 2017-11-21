package thread.reentrantReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
	
	private int counter = 1;
	
	private ReentrantReadWriteLock myLock = new ReentrantReadWriteLock();
	private Lock readLock = myLock.readLock();
	private Lock writeLock = myLock.writeLock();
	
	public ReentrantReadWriteLockTest() throws InterruptedException {
		
		System.out.println("Without Lock:");
		readLock();
		Thread.sleep(500);
		System.out.println("\nWith Lock:");
		counter = 1;
		writeLock();
		
	}
	
	private void readLock() {
		Runnable r = () -> {
			readLock.lock();	//Doesnt work, because we write here
			try {	    
				int c = getCounter();
				c++;
				System.out.print(counter+", ");
				setCounter(c);
			} finally {
				readLock.unlock();
			}
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	private void writeLock() {
		Runnable r = () -> {
			writeLock.lock();
			try {	    
				int c = getCounter();
				c++;
				System.out.print(counter+", ");
				setCounter(c);
			} finally {
				writeLock.unlock();
			}
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new ReentrantReadWriteLockTest();
	}
	
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
