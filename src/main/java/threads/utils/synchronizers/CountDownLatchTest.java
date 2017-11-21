package threads.utils.synchronizers;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	
	private CountDownLatch latch = new CountDownLatch(3);
	
	public CountDownLatchTest() throws InterruptedException {
		
		Runnable decrementer = () -> {
			System.out.println("Couner at start: "+latch.getCount());
			try {
	            Thread.sleep(1000);
	            this.latch.countDown();
	            System.out.println("Couner: "+latch.getCount());
	            
	            Thread.sleep(1000);
	            this.latch.countDown();
	            System.out.println("Couner: "+latch.getCount());
	            
	            Thread.sleep(1000);
	            this.latch.countDown();
	            System.out.println("Couner: "+latch.getCount());
	            
	            Thread.sleep(1000);
	            this.latch.countDown();
	            System.out.println("countDown() after reached 0: "+latch.getCount());
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    };
		
		Runnable waiter = () -> {
			try {
	            latch.await();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        System.out.println("--- Waiting Thread Released!");
	    };

		new Thread(waiter).start();
		new Thread(decrementer).start();

	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new CountDownLatchTest();
	}

}
