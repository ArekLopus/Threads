package threads.utils.executors.ForkJoinPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveTaskSimple {

	private ForkJoinPool fjp = new ForkJoinPool(4);
	private int toCount = 50000;
	
	public RecursiveTaskSimple() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();
		
		MyRecursiveTask t1 = new MyRecursiveTask(toCount);
		fjp.submit(t1);
		
		System.out.println("All: "+t1.join());
		System.out.println("Passes: "+MyRecursiveTask.passes);
		
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new RecursiveTaskSimple();
	}

}

class MyRecursiveTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	public static int passes = 0;
	int all = 0;
	
	public MyRecursiveTask() {}
	public MyRecursiveTask(int i) {
		this.all = i;
	}
	
	@Override
	protected Integer compute() {
		int ret = 0;
		
		if(all < 10001) {
			ret = sum(all);
			MyRecursiveTask.passes += 1;
			return all;
		}
		
		MyRecursiveTask t1 = new MyRecursiveTask(all/2);
		MyRecursiveTask t2 = new MyRecursiveTask(all/2);
		
		RecursiveTask.invokeAll(t1, t2);
		ret = t2.join() + t1.join();
		
		return ret;
	}
	
	private int sum(int i) {
		int sum = 0;
		for(int j=0; j<i; j++) {
			sum = sum + j;
		}
		return sum;
	}
}