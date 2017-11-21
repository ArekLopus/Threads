package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//False Sharing
//Whenever the program updates a value in its local cache, that core must notify all the other cores that the memory in question has been changed.
//Those other cores must invalidate their cache lines and reload that data from memory.
//Strictly speaking, false sharing does not have to involve synchronized (or volatile) 1variables:
//whenever any data value in the CPU cache is written, other caches that hold the same data range must be invalidated
public class FalseSharingTest extends Thread {
	
	public static ExecutorService es1 = Executors.newFixedThreadPool(1);
	public static ExecutorService es4 = Executors.newFixedThreadPool(4);
	private static DataHolder dh = new DataHolder();
	private static long nLoops = 1_000_000L;
	
	private static class DataHolder {
		private volatile long l1 = 0;
		private volatile long l2 = 0;
		private volatile long l3 = 0;
		private volatile long l4 = 0;
	}
//	private static class DataHolder {
//		private long l1 = 0;
//		private long l2 = 0;
//		private long l3 = 0;
//		private long l4 = 0;
//	}


	public FalseSharingTest(Runnable r) {
		super(r);
	}

	public static void main(String[] args) throws Exception {

		FalseSharingTest[] tests = new FalseSharingTest[4];
		tests[0] = new FalseSharingTest( () -> {
			for (long i = 0; i < nLoops; i++) {
				dh.l1 += i;
			}
		});
		tests[1] = new FalseSharingTest( () -> {
			for (long i = 0; i < nLoops; i++) {
				dh.l2 += i;
			}
		});
		tests[2] = new FalseSharingTest( () -> {
			for (long i = 0; i < nLoops; i++) {
				dh.l3 += i;
			}
		});
		tests[3] = new FalseSharingTest( () -> {
			for (long i = 0; i < nLoops; i++) {
				dh.l4 += i;
			}
		});
		
		List<Future<?>> tests2 = new ArrayList<>();
		long then = System.currentTimeMillis();
		for (FalseSharingTest ct : tests) {
			//ct.start();
			Future<?> submit = es4.submit(ct);
			tests2.add(submit);
		}
//		for (ContendedTest ct : tests) {
//			//ct.join();
//		}
		for (Future<?> ct : tests2) {
			ct.get();
		}
		long now = System.currentTimeMillis();
		System.out.println("Duration: " + (now - then) + " ms");
		
		es1.shutdown();
		es4.shutdown();
	}
}