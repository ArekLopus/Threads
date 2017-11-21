package threads.utils.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayInt {
	
	private int[] counter = new int[1];
	private AtomicIntegerArray aia = new AtomicIntegerArray(counter);
	
	public AtomicArrayInt() throws InterruptedException {
		
		sync();
		Thread.sleep(150);
		System.out.println("\nSynchronized: "+aia.get(0));
		
		notSync();
		Thread.sleep(50);
		System.out.println("\nNot Synchronized: "+counter[0]);

	}

	private void sync() {
		Runnable r = () -> {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print(aia.incrementAndGet(0)+", ");
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	private void notSync() {
		Runnable r = () -> {
			int c = getCounter();
			c++;
			System.out.print(counter[0]+", ");
			setCounter(c);
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new AtomicArrayInt();
	}

	public int getCounter() {
		return counter[0];
	}

	public void setCounter(int counter) {
		this.counter[0] = counter;
	}
	
	
}
