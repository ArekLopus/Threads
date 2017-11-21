package thread.reenterantLock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

//Acquires the lock only if it is free at the time of invocation.
//Acquires the lock if it is available and returns immediately with the value true. If the lock is not available then this method will return immediately with the value false. 
public class TryLock implements Runnable {
	
	ReentrantLock lock = new ReentrantLock();
	
	public TryLock() throws InterruptedException {
		
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
			System.out.println(Thread.currentThread().getName()+"\tSleep: "+sleep);
		};
		
		Runnable r2 = () -> {
			try {
				Thread.sleep(500);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if(lock.tryLock()) {
				try {
					System.out.println("Lock hold count: "+lock.getHoldCount());
					System.out.println(Thread.currentThread().getName()+"\ttryLock()");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
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
		new TryLock();
	}
	
}
