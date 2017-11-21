package threads.utils.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInt {
	
	private AtomicInteger ai = new AtomicInteger();
	private int counter = 0;
	
	public AtomicInt() throws InterruptedException {
		
		sync();
		Thread.sleep(150);
		System.out.println("\nSynchronized: "+ai.get());
		
		notSync();
		Thread.sleep(50);
		System.out.println("\nNot Synchronized: "+counter);

	}

	private void sync() {
		Runnable r = () -> {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print(ai.incrementAndGet()+", ");
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	private void notSync() {
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
	
	
	public static void main(String[] args) throws InterruptedException {
		new AtomicInt();
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
}
