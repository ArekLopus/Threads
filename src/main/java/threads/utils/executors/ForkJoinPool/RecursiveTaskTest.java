package threads.utils.executors.ForkJoinPool;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class RecursiveTaskTest {

	public RecursiveTaskTest() throws InterruptedException {
		
		long start = System.nanoTime();
		
		ForkJoinTask<Integer> task1 = new ARecursiveTaskTest();
		ForkJoinTask<Integer> task2 = new ARecursiveTaskTest();
		ForkJoinTask<Integer> task3 = new ARecursiveTaskTest();
		task1.fork();
		task2.fork();
		task3.fork();
		System.out.println("All: "+(task1.join()+task2.join()+task3.join()));
		
		System.out.println("--= Done in "+((System.nanoTime() - start) / 1_000_000)+" ms =--");
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new RecursiveTaskTest();
	}

}

class ARecursiveTaskTest extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;

	@Override
	protected Integer compute() {
		int ret = 0;
		try {
			ret = ThreadLocalRandom.current().nextInt(10);
			ret = (ret+1)*100;
			System.out.println("Sleep: "+ret+", "+Thread.currentThread().getName());
			Thread.sleep(ret);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}