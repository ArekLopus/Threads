package threads.utils.blockingQueues;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {
	
	private LinkedBlockingQueue<Integer> bq = new LinkedBlockingQueue<>();
	
	public LinkedBlockingQueueTest() throws InterruptedException {
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
		new LinkedBlockingQueueTest();
	}
	
	
}
