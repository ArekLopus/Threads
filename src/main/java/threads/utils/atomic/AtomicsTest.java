package threads.utils.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicsTest {
	
	private AtomicInteger ai = new AtomicInteger();
	
	public AtomicsTest() throws InterruptedException {
		
		Runnable consumer = () -> {
		  while(true) {
			synchronized (ai) {
			while (ai.get() == 0) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ai.set(0);
			System.out.println("consumed:- "+Thread.currentThread().getName());
			}
		  }
		};
		
		Runnable producer = () -> {
		  while(true) {
			while (ai.get() > 0) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ai.set(1);
			System.out.println("produced: "+Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
		};
		
		new Thread(producer).start();
		//Thread.sleep(20);
		for(int i=0; i<10; i++) {
			new Thread(consumer).start();
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		new AtomicsTest();
	}

}
