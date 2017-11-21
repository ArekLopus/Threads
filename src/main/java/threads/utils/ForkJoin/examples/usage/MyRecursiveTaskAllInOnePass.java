package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTaskAllInOnePass extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	int workLoad = 0;
	
    public MyRecursiveTaskAllInOnePass(int workLoad) {
        this.workLoad = workLoad;
    }

    protected Integer compute() {

    	if( workLoad > 1) {
      		 
    		List<MyRecursiveTaskAllInOnePass> subtasks = new ArrayList<MyRecursiveTaskAllInOnePass>();
    		subtasks.addAll(createSubtasks());

    		RecursiveTask.invokeAll(subtasks);
    		
    		int result = 0;
    		for(MyRecursiveTaskAllInOnePass subtask : subtasks) {
    			result += subtask.join();
    		}
    		return result;
    		
    	} else {
    		return doIt(100);
    	}
    }
    
    private List<MyRecursiveTaskAllInOnePass> createSubtasks() {
        
    	List<MyRecursiveTaskAllInOnePass> subtasks = new ArrayList<MyRecursiveTaskAllInOnePass>();
    	//We create ALL subtasks at once here
    	for(int i=0; i<workLoad; i++) {
    		MyRecursiveTaskAllInOnePass subtask = new MyRecursiveTaskAllInOnePass(1);
    		subtasks.add(subtask);
    	}
    	
        return subtasks;
    }

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);
		
		long start = System.nanoTime();

		MyRecursiveTaskAllInOnePass myRecursiveTask = new MyRecursiveTaskAllInOnePass(400);
		int mergedResult = forkJoinPool.invoke(myRecursiveTask);

		System.out.println("mergedResult = " + mergedResult);
		
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
	}
	
	private Integer doIt(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Sleeps: "+sleep+", "+Thread.currentThread().getName());
		return sleep;
	}
	
}
