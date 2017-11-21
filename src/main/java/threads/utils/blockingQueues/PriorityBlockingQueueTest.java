package threads.utils.blockingQueues;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueTest {
	
	PriorityBlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
	
	public PriorityBlockingQueueTest() throws InterruptedException {
		
		pbq.put(7);
		pbq.put(5);
		pbq.put(3);
		pbq.put(1);
		pbq.put(9);
		
		Runnable r = () -> {
			try {
				System.out.println(pbq.take()+", "+Thread.currentThread().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		for(int i=0; i<5; i++) {
			new Thread(r).start();
			Thread.sleep(50);
		}
		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new PriorityBlockingQueueTest();
	}

}
