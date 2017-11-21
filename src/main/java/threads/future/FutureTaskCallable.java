package threads.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskCallable {

	public FutureTaskCallable() throws InterruptedException, ExecutionException {
		
		Callable<Integer> c = () -> {
			System.out.println("Callable started...");
			Thread.sleep(1000);
			return 5;
		};
		
		FutureTask<Integer> task = new FutureTask<>(c);
		new Thread(task).start();		// it's a Runnable
		
		while(!task.isDone()) {
			Thread.sleep(400);
			System.out.println("Sleeping...");
		} 
		
		System.out.println("Task is done: "+task.get());
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new FutureTaskCallable();
	}

}
