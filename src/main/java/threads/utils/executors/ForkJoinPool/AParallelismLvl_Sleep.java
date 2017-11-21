package threads.utils.executors.ForkJoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class AParallelismLvl_Sleep extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	int workLoad = 0;
	
    public AParallelismLvl_Sleep(int workLoad) {
        this.workLoad = workLoad;
    }
    
    //There are 400 tasks to do
	public static void main(String[] args) {
		//ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());		//10088 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(10);		//4280 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(100);	//603 ms
		ForkJoinPool forkJoinPool = new ForkJoinPool(400);	//229 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(2*400);	//240 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);	//250 ms - Creating more threads has time penalty 
		
		long start = System.nanoTime();

		AParallelismLvl_Sleep myRecursiveTask = new AParallelismLvl_Sleep(400);
		
		//int mergedResult = forkJoinPool.invoke(myRecursiveTask);
		int mergedResult = forkJoinPool.submit(myRecursiveTask).join();
		
		System.out.println("mergedResult = " + mergedResult);
		
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
	}
	
    
    protected Integer compute() {

    	if( workLoad > 1) {
      		 
    		List<AParallelismLvl_Sleep> subtasks = new ArrayList<AParallelismLvl_Sleep>();
    		subtasks.addAll(createSubtasks());									//split problem into independent parts

    		RecursiveTask.invokeAll(subtasks);									//fork new subtasks to solve each part
    		
    		int result = 0;

    		for(AParallelismLvl_Sleep subtask : subtasks) {							//join all subtasks
    			result += subtask.join();										//compose result from subresults
    		}
    		return result;
    	} else {
    		return doIt(100);													//directly solve problem
    	}
    }
    
    private List<AParallelismLvl_Sleep> createSubtasks() {
        
    	List<AParallelismLvl_Sleep> subtasks = new ArrayList<AParallelismLvl_Sleep>();
    	
    	int middle = workLoad / 2;
    	
    	int low = middle;
    	int high = workLoad - middle;
    	
    	AParallelismLvl_Sleep subtask1 = new AParallelismLvl_Sleep(low);
        AParallelismLvl_Sleep subtask2 = new AParallelismLvl_Sleep(high);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
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
