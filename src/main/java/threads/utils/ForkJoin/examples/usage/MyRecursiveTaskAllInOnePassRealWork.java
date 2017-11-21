package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTaskAllInOnePassRealWork extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	int workLoad = 0;
	
    public MyRecursiveTaskAllInOnePassRealWork(int workLoad) {
        this.workLoad = workLoad;
    }

    protected Integer compute() {

    	if( workLoad > 1) {
      		 
    		List<MyRecursiveTaskAllInOnePassRealWork> subtasks = new ArrayList<MyRecursiveTaskAllInOnePassRealWork>();
    		subtasks.addAll(createSubtasks());

    		RecursiveTask.invokeAll(subtasks);
    		
    		int result = 0;
    		for(MyRecursiveTaskAllInOnePassRealWork subtask : subtasks) {
    			result += subtask.join();
    		}
    		return result;
    		
    	} else {
    		return doIt(100);
    	}
    }
    
    private List<MyRecursiveTaskAllInOnePassRealWork> createSubtasks() {
        
    	List<MyRecursiveTaskAllInOnePassRealWork> subtasks = new ArrayList<MyRecursiveTaskAllInOnePassRealWork>();
    	//We create ALL subtasks at once here
    	for(int i=0; i<workLoad; i++) {
    		MyRecursiveTaskAllInOnePassRealWork subtask = new MyRecursiveTaskAllInOnePassRealWork(1);
    		subtasks.add(subtask);
    	}
    	
        return subtasks;
    }

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);
		
		long start = System.nanoTime();

		MyRecursiveTaskAllInOnePassRealWork myRecursiveTask = new MyRecursiveTaskAllInOnePassRealWork(400);
		int mergedResult = forkJoinPool.invoke(myRecursiveTask);

		System.out.println("mergedResult = " + mergedResult);
		
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
	}
	
	private Integer doIt(int multiply) {
		int loops = multiply * 100_000;
		int[] arr = new int[loops];
		for(int i=0; i<loops; i++) {
			arr[i] = 123;
		}
		
		return multiply;
	}
	
}
