package threads.utils.synchronizers;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {
	
	private SynchronousQueue<Integer> sq = new SynchronousQueue<>();
	
	public SynchronousQueueTest() throws InterruptedException {
		
		Runnable prod = () -> {
			for(int i=0; i<5; i++) {
				try {
					Thread.sleep(50);
					sq.put(1);
					System.out.println("Produced");
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Runnable cons = () -> {
			try {
				sq.take();
				System.out.println("---Consumed, "+Thread.currentThread().getName());
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		new Thread(prod).start();
		for(int i=0; i<5; i++) {
			new Thread(cons).start();
		}
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new SynchronousQueueTest();
	}

}
