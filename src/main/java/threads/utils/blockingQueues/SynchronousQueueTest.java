package threads.utils.blockingQueues;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {
	
	private SynchronousQueue<Integer> bq = new SynchronousQueue<>();
	
	public SynchronousQueueTest() throws InterruptedException {
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
		new Thread(consumer).start();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new SynchronousQueueTest();
	}
	
	
}
