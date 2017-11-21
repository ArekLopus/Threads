package thread.reenterantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//!!A thread can only call await(), signalAll(), or signal() on a condition if it owns the lock of the condition.!!
public class ReentrantLockCondition {
	
	private int counter = 1;
	
	private Lock myLock = new ReentrantLock();
	private Condition valueOf2 = myLock.newCondition();
	
	public ReentrantLockCondition() throws InterruptedException {
		withLock();
	}
	
	private void withLock() {
		Runnable r = () -> {
			myLock.lock(); 				// a ReentrantLock object
			try { 
				int c = getCounter();
				if(c == 2) {
					valueOf2.signalAll();	//signals to await() object to run.
				}
				c++;
				System.out.println("Counter="+counter+", "+Thread.currentThread().getName());
				setCounter(c);
			} finally {
				myLock.unlock(); 		// makes sure the lock is unlocked even if an exception is thrown
			}
		};
		
		Runnable r2 = () -> {
			myLock.lock();	//A thread can only call await()/signalAll() on a condition if it owns the lock of the condition
			try {	    
				if (getCounter() <2) {
					valueOf2.await();	//awaits until signal()/signalAll() called from another thread
				}
				System.out.println("-+= Activated after counter reached 2! =+-"+Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				myLock.unlock();
			}
		};

		Thread th = new Thread(r2);
		th.start();
		
		for(int i=0; i<8; i++) {
			new Thread(r).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new ReentrantLockCondition();
	}
	
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
