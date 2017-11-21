package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTaskRealWork extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	int workLoad = 0;
	
    public MyRecursiveTaskRealWork(int workLoad) {
        this.workLoad = workLoad;
    }

    protected Integer compute() {

    	if( workLoad > 1) {
      		 
    		List<MyRecursiveTaskRealWork> subtasks = new ArrayList<MyRecursiveTaskRealWork>();
    		subtasks.addAll(createSubtasks());									//split problem into independent parts

    		RecursiveTask.invokeAll(subtasks);									//fork new subtasks to solve each part
    		
    		int result = 0;

    		for(MyRecursiveTaskRealWork subtask : subtasks) {					//join all subtasks
    			result += subtask.join();										//compose result from subresults
    		}
    		return result;
    	} else {
    		return doIt(100);													//directly solve problem
    	}
    }
    
    private List<MyRecursiveTaskRealWork> createSubtasks() {
        
    	List<MyRecursiveTaskRealWork> subtasks = new ArrayList<MyRecursiveTaskRealWork>();
    	
    	int middle = workLoad / 2;
    	
    	int low = middle;
    	int high = workLoad - middle;
    	
    	MyRecursiveTaskRealWork subtask1 = new MyRecursiveTaskRealWork(low);
        MyRecursiveTaskRealWork subtask2 = new MyRecursiveTaskRealWork(high);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);
		
		long start = System.nanoTime();

		MyRecursiveTaskRealWork myRecursiveTask = new MyRecursiveTaskRealWork(400);
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
