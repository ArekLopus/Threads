package thread;

import java.util.concurrent.ThreadLocalRandom;

public class Join {
	
	public Join() throws InterruptedException {
		
		Runnable r = () -> {
			int sleep = (ThreadLocalRandom.current().nextInt(10)+1)*100;
			try {
				Thread.sleep(sleep);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"\tSleep: "+sleep);
		};
		
		Thread t = new Thread(r);
		t.start();
		
		t.join();
		System.out.println("Join waits until the thread has completed.");
	}
	
	
	public static void main(String[] args) throws Exception {
		new Join();
	}
	
}
