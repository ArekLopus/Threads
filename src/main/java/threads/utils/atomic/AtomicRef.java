package threads.utils.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRef {
	
	private Integer counter = new Integer(0);
	private AtomicReference<Integer> ar = new AtomicReference<>(0);
	
	public AtomicRef() throws InterruptedException {
		
		sync();
		Thread.sleep(150);
		System.out.println("\nSynchronized: "+ar.get());
		
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
			System.out.print(ar.updateAndGet( i -> i+1 ) +", ");
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
		new AtomicRef();
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
	
}
