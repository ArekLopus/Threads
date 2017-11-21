package thread.reenterantLock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

//Acquires the lock if it is free within the given waiting time and the current thread has not been interrupted. 
//InterruptedException - if the current thread is interrupted while acquiring the lock (and interruption of lock acquisition is supported)
public class TryLockWithTimeout implements Runnable {
	
	ReentrantLock lock = new ReentrantLock();
	
	public TryLockWithTimeout() throws InterruptedException {
		
		Runnable r = () -> {
			lock.lock();
			int sleep = (ThreadLocalRandom.current().nextInt(10)+1)*100;
			System.out.println("Lock hold count: "+lock.getHoldCount());
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			System.out.println(Thread.currentThread().getName()+"\t\tSleep: "+sleep);
		};
		
		Runnable r2 = () -> {
			try {

				if(lock.tryLock(500, TimeUnit.MILLISECONDS)) {
					try{
						System.out.println("Lock hold count: "+lock.getHoldCount());
						System.out.println(Thread.currentThread().getName()+"\t\ttryLock()");
					} finally {
						lock.unlock();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		new Thread(r).start();
		new Thread(r2).start();
		
		Thread.sleep(1100);
		System.out.println("Lock hold count: "+lock.getHoldCount());
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"\tClass implements Runnable\t\t");
	}
	
	
	
	public static void main(String[] args) throws Exception {
		new TryLockWithTimeout();
	}
	
}
