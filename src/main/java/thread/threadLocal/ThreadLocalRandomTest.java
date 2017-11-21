package thread.threadLocal;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTest {
	
	public ThreadLocalRandomTest() {

	
		Runnable r = () -> {
			int in = ThreadLocalRandom.current().nextInt(10)+1;		//1 - 10
			System.out.println("ThreadLocalRandom.current().nextInt(10): "+in);
		};
		
		for(int i=0; i<5; i++) {
			new Thread(r).start();
		}
}
	
	public static void main(String[] args) throws InterruptedException {
		new ThreadLocalRandomTest();
	}

}
