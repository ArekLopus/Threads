package threads.utils.executors.ForkJoinPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class InvokeTasks {

	public InvokeTasks() throws InterruptedException {
		
		long start = System.nanoTime();
		ForkJoinTask<Integer> task1 = new MyRecursiveTaskTest();
		ForkJoinTask<Integer> task2 = new MyRecursiveTaskTest();
		ForkJoinTask<Integer> task3 = new MyRecursiveTaskTest();
		
		ForkJoinPool fjp = new ForkJoinPool(4);
		
//		fjp.execute(task1);		//returns void
//		fjp.execute(task2);
//		fjp.execute(task3);
		
		fjp.submit(task1);		//returns ForkJoinTask<Integer> which is a lightweight form of Future (ForkJoinTask<V> implements Future<V>)
		fjp.submit(task2);
		fjp.submit(task3);
		
//		fjp.invoke(task1);		//all submits use ForkJoinPool-1-worker-1 -> RUNS SEQUENTIALLY!!!!!
//		fjp.invoke(task2);
//		fjp.invoke(task3);		//returns value (Integer)
		
		//java.util.concurrent.RecursiveTask.invokeAll(task1, task2, task3);
		//java.util.concurrent.RecursiveAction.invokeAll(task1, task2, task3);
		
		System.out.println("All: "+(task1.join()+task2.join()+task3.join()));
		
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		
		fjp.shutdown();
		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new InvokeTasks();
	}

}

class MyRecursiveTaskTest extends RecursiveTask<Integer> {
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