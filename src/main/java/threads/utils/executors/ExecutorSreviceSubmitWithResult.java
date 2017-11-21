package threads.utils.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class ExecutorSreviceSubmitWithResult {

	public ExecutorSreviceSubmitWithResult() throws InterruptedException, ExecutionException {
		
		Runnable r = () -> {
			int time = (ThreadLocalRandom.current().nextInt(10)+1)*100;
			System.out.println("Time: "+time+", thread: "+Thread.currentThread().getName()+", "+this);
			try {
				Thread.sleep(time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		
		Future<?> f = es.submit(r, 10);
		System.out.println(f.get());
		
		es.shutdown();
		
		//What does 'result' in ExecutorService.submit(Runnable task, T result) do?
		//It doesn't do anything with the result - just holds it. When the task successfully completes,
		//calling future.get() will return the result you passed in. 
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		new ExecutorSreviceSubmitWithResult();
	}

}
