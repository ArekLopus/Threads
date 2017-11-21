package threads.utils.executors.ForkJoinPool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class RecursiveTaskTest2 {
	
	int size = 10_000_000;
	int[] ar = new int[size];
	
	public RecursiveTaskTest2() throws InterruptedException {
		
		ForkJoinPool fp = new ForkJoinPool(4);
		System.out.println(fp);
		
		for(int i=0; i<size; i++) {
			ar[i] = ThreadLocalRandom.current().nextInt(10)+1; 
		}
		
		long start = System.currentTimeMillis();
		int sum = 0;
		for(int i=0; i<size; i++) {
			sum = sum + ar[i];
		}
		System.out.println("Loop Sum =\t"+sum+", Time: "+(System.currentTimeMillis() - start)+" ms");
		
		
		start = System.currentTimeMillis();
		sum = Arrays.stream(ar).reduce(0, (x, y) -> x+y);
		//sum = Arrays.stream(ar).parallel().reduce(0, (x, y) -> x+y);
		//OptionalInt oi = Arrays.stream(ar).reduce(Integer::sum);
		//System.out.println("Sum = "+oi.getAsInt()+", Time: "+(System.currentTimeMillis() - start)+" ms");
		System.out.println("Stream Sum =\t"+sum+", Time: "+(System.currentTimeMillis() - start)+" ms");
		
		//sum = 0;
		start = System.currentTimeMillis();
		ARecursiveTaskTest2 rt = new ARecursiveTaskTest2(ar);
		fp.execute(rt);
		sum = rt.join();
		System.out.println("Task Sum =\t"+sum+", Time: "+(System.currentTimeMillis() - start)+" ms");
		
		//sum = 0;
		start = System.currentTimeMillis();
		ARecursiveTaskTest3 rt2 = new ARecursiveTaskTest3(ar);
		fp.execute(rt2);
		sum = rt2.join();
		System.out.println("Task2 Sum =\t"+sum+", Time: "+(System.currentTimeMillis() - start)+" ms");
		
		fp.shutdown();
		System.out.println(fp);
	}
	
	public static void main(String[] args) throws InterruptedException {
		new RecursiveTaskTest2();
	}

}

class ARecursiveTaskTest2 extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	private int[] ar;
	
	public ARecursiveTaskTest2(int[] ar) {
		this.ar = ar;
	}

	int sum = 0;
	
	@Override
	protected Integer compute() {
		
		for(int i=0; i<ar.length; i++) {
			sum = sum + ar[i];
		}
		return sum;
	}
	
}
class ARecursiveTaskTest3 extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	private int[] ar;
	
	public ARecursiveTaskTest3(int[] ar) {
		this.ar = ar;
	}

	int sum = 0;
	
	@Override
	protected Integer compute() {
		
		if(ar.length < 1000001) {
			for(int i=0; i<ar.length; i++) {
				sum = sum + ar[i];
			}
			return sum;
		} else {
			int middle = ar.length /2;
			int[] left = Arrays.copyOfRange(ar, 0 , middle );
		    int[] right = Arrays.copyOfRange(ar, middle, ar.length );
			
			ARecursiveTaskTest3 rt1 = new ARecursiveTaskTest3(left);
			ARecursiveTaskTest3 rt2 = new ARecursiveTaskTest3(right);
			
			RecursiveTask.invokeAll(rt1, rt2);
			//or
			//rt1.fork();			//rt2.fork();
			
			return rt1.join() + rt2.join();
		}
	}

}