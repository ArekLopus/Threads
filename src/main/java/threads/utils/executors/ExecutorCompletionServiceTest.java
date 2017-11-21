package threads.utils.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ExecutorCompletionServiceTest {
	
	private int loops = 10;
	private ExecutorCompletionService<Integer> service = new ExecutorCompletionService<>(Executors.newFixedThreadPool(loops));
	
	public ExecutorCompletionServiceTest() throws InterruptedException, ExecutionException {
		
		for (int i=0; i<loops; i++) {
			service.submit(c);
		}
			
		for (int i=0; i<loops; i++) {
			System.out.print(service.take().get()+", ");	//take Future, get value
		}

	}

	Callable<Integer> c = () -> {
		int time = (ThreadLocalRandom.current().nextInt(10)+1)*100;
		System.out.println("Time: "+time+", thread: "+Thread.currentThread().getName());
		Thread.sleep(time);
		return time;
	};
	
	
	public static void main(String[] args) throws Exception {
		new ExecutorCompletionServiceTest();
	}

}
