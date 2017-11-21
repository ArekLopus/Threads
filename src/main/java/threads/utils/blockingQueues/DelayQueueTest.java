package threads.utils.blockingQueues;

import java.util.concurrent.DelayQueue;

public class DelayQueueTest {

	final DelayQueue<DelayElement> queue = new DelayQueue<>();
	
	public static void main(String[] args) throws Exception {
		new DelayQueueTest().simple();
	}
	
	public void simple() throws InterruptedException {
		
		DelayElement delayElement1 = new DelayElement("No 1", 3000);
		DelayElement delayElement2 = new DelayElement("No 2", 1500);
		
		queue.put(delayElement1);
		queue.put(delayElement2);
		System.out.println("Put el: "+delayElement1);
		System.out.println("Put el: "+delayElement2);
		
		DelayElement recElement1 = queue.take();
		System.out.println(Thread.currentThread().getName() + " take(): " + recElement1);
		DelayElement recElement2 = queue.take();
		System.out.println(Thread.currentThread().getName() + " take(): " + recElement2);

	}
	
}

