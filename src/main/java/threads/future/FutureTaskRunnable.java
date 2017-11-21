package threads.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskRunnable {

	public FutureTaskRunnable() throws InterruptedException, ExecutionException {
		
		Runnable r = () -> {
			System.out.println("Runnable started...");
		};
		
		FutureTask<Integer> task = new FutureTask<>(r, 123);
		new Thread(task).start();
		
		System.out.println("Task is done: "+task.get());
	}
	
	
	public static void main(String[] args) throws Exception {
		new FutureTaskRunnable();
	}

}
