package threads.utils.blockingQueues;

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeTest {
	
	private LinkedBlockingDeque<Integer> bq = new LinkedBlockingDeque<>();
	
	public LinkedBlockingDequeTest() throws InterruptedException {
		start();
	}
	
	
	private void start() {
		Runnable producer = () -> {
			while(true) {
				try {
					Thread.sleep(2000);
					System.out.println("Produced: "+Thread.currentThread().getName());
					bq.put(1);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Runnable consumer = () -> {
			while(true) {
				try {
					bq.take();
					System.out.println("--Consumed: "+Thread.currentThread().getName());

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};

		new Thread(producer).start();
		for(int i=0; i<5; i++) {
			new Thread(consumer).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new LinkedBlockingDequeTest();
	}
	
	
}
