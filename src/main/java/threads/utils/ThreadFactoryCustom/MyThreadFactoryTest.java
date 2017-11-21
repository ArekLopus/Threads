package threads.utils.ThreadFactoryCustom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadFactoryTest {

	public MyThreadFactoryTest() throws InterruptedException {
		
		ExecutorService es = Executors.newFixedThreadPool(14, new MyThreadFactory()
			.setDaemon(true)
			.setNameFormat("MyThread-%d")
			.build());
		
		Runnable r = () -> {
			System.out.println("Runnable, thread: "+Thread.currentThread().getName());
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		for(int i = 0; i<4; i++) {
			es.submit(r);
		}
//		System.out.println();
//		Thread.sleep(20);
		for(int i = 0; i<4; i++) {
			es.submit(r);
		}
		
		Thread.sleep(1200);
		es.shutdown();
		
		System.out.println();
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new MyThreadFactoryTest();
	}

}
