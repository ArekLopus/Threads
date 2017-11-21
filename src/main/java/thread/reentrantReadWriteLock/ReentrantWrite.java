package thread.reentrantReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//The write operation needs to acquire the write lock, which is granted only if there is no other process that already
//holds the read or write locks. Write access is exclusive.
//No other process may be reading or writing when a given process holds the write lock. 
public class ReentrantWrite {
	
	private int counter = 1;
	private long timer = System.currentTimeMillis();
	
	private ReentrantReadWriteLock myLock = new ReentrantReadWriteLock();
	private Lock readLock = myLock.readLock();
	private Lock writeLock = myLock.writeLock();
	
	public ReentrantWrite() throws InterruptedException {
		readLock();
	}
	
	private void readLock() {
		Runnable r = () -> {
			synchronized (this) {	//You need to synchronize these 2 operations with writeLock() or synchronized
				setCounter(getCounter() + 1);
				System.out.println("Counter: "+getCounter()+", taken: "+(System.currentTimeMillis() - timer) );
			}
		};
		
		for(int i=0; i<10; i++) {
			new Thread(r).start();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ReentrantWrite();
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
