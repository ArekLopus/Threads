package thread.reenterantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//!!A thread can only call await(), signalAll(), or signal() on a condition if it owns the lock of the condition.!!
//await() - until a condition is true, signal() - when the condition is true
public class Await {
	
	private boolean check = false;
	
	private Lock myLock = new ReentrantLock();
	private Condition con = myLock.newCondition();
	
	public Await() throws InterruptedException {
		withLock();
	}
	
	private void withLock() {
		Runnable r = () -> {
			myLock.lock();
			try {
				try {
					Thread.sleep(700);
					check = true;
					con.signal();	//signals await
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} finally {
				myLock.unlock();
			}
		};
		
		Runnable r2 = () -> {
			myLock.lock();
			try {	    
				while (check != true) {
					con.await();	//awaits until signal()/signalAll() called from another thread
				}
				System.out.println("await() was activated by signal(), "+Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				myLock.unlock();
			}
		};

		new Thread(r).start();
		new Thread(r2).start();
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new Await();
	}
	
	
	
}
