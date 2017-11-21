package thread.reentrantReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//Each time a given process needs to read the information it must acquire a Read lock, which is granted if there is 
//no other process currently holding the Write lock (the one that a process need to acquire before any write operation).
//Multiple processes may access the information at the same time if they only need the read lock (read access is shared). 
public class ReentrantRead {
	
	private int counter = 1;
	private long timer = System.currentTimeMillis();
	
	private ReentrantReadWriteLock myLock = new ReentrantReadWriteLock();
	private Lock readLock = myLock.readLock();
	private Lock writeLock = myLock.writeLock();
	
	public ReentrantRead() throws InterruptedException {
		readLock();
	}
	
	private void readLock() {
		Runnable r = () -> {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Counter: "+getCounter()+", taken: "+(System.currentTimeMillis() - timer));
		};
		
		for(int i=0; i<10; i++) {
			new Thread(r).start();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ReentrantRead();
	}
	
	
	public int getCounter() {
		readLock.lock();
		try {
			return counter;
		} finally {
			readLock.unlock();
		}
	}
	
	public void setCounter(int counter) {
		writeLock.lock();
		try {
			Thread.sleep(500);
			this.counter = counter;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			writeLock.unlock();
		}
	}
	
	
	
}
