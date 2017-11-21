package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	int workLoad = 0;
	
    public MyRecursiveTask(int workLoad) {
        this.workLoad = workLoad;
    }

    protected Integer compute() {

    	if( workLoad > 1) {
      		 
    		List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();
    		subtasks.addAll(createSubtasks());									//split problem into independent parts

    		RecursiveTask.invokeAll(subtasks);									//fork new subtasks to solve each part
    		
    		int result = 0;

    		for(MyRecursiveTask subtask : subtasks) {							//join all subtasks
    			result += subtask.join();										//compose result from subresults
    		}
    		return result;
    	} else {
    		return doIt(100);													//directly solve problem
    	}
    }
    
    private List<MyRecursiveTask> createSubtasks() {
        
    	List<MyRecursiveTask> subtasks = new ArrayList<MyRecursiveTask>();
    	
    	int middle = workLoad / 2;
    	
    	int low = middle;
    	int high = workLoad - middle;
    	
    	MyRecursiveTask subtask1 = new MyRecursiveTask(low);
        MyRecursiveTask subtask2 = new MyRecursiveTask(high);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(10);
		//ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);
		
		long start = System.nanoTime();

		MyRecursiveTask myRecursiveTask = new MyRecursiveTask(400);
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
