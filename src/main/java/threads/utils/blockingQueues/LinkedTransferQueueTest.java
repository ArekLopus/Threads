package threads.utils.blockingQueues;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueTest {
	
	LinkedTransferQueue<String> ltq = new LinkedTransferQueue<>();
	
	public LinkedTransferQueueTest() throws InterruptedException {
		
		Runnable producer = () -> {
			try {
				ltq.transfer("String1");
				Thread.sleep(1000);
				ltq.transfer("String2");
				Thread.sleep(1500);
				ltq.transfer("String3");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		};
		
		Runnable consumer = () -> {
			for(int i=0; i<3; i++) {
				String s;
				try {
					s = ltq.take();
					System.out.println(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		new Thread(producer).start();
		new Thread(consumer).start();
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new LinkedTransferQueueTest();
	}

}
