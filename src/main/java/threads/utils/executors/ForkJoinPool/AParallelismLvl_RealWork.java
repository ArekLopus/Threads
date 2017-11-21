package threads.utils.executors.ForkJoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class AParallelismLvl_RealWork extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	
	int workLoad = 0;
	
    public AParallelismLvl_RealWork(int workLoad) {
        this.workLoad = workLoad;
    }
    
    //There are 400 tasks to do
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());		//799 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(10);		//777 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(100);	//800 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(400);	//837 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(2*400);	//863 ms
		//ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);	//835 ms 
		
		long start = System.nanoTime();

		AParallelismLvl_RealWork myRecursiveTask = new AParallelismLvl_RealWork(400);
		
		//int mergedResult = forkJoinPool.invoke(myRecursiveTask);
		int mergedResult = forkJoinPool.submit(myRecursiveTask).join();
		
		System.out.println("mergedResult = " + mergedResult);
		
		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		
	}
	
    
    protected Integer compute() {

    	if( workLoad > 1) {
      		 
    		List<AParallelismLvl_RealWork> subtasks = new ArrayList<AParallelismLvl_RealWork>();
    		subtasks.addAll(createSubtasks());									//split problem into independent parts

    		RecursiveTask.invokeAll(subtasks);									//fork new subtasks to solve each part
    		
    		int result = 0;

    		for(AParallelismLvl_RealWork subtask : subtasks) {							//join all subtasks
    			result += subtask.join();										//compose result from subresults
    		}
    		return result;
    	} else {
    		return doIt(100);													//directly solve problem
    	}
    }
    
    private List<AParallelismLvl_RealWork> createSubtasks() {
        
    	List<AParallelismLvl_RealWork> subtasks = new ArrayList<AParallelismLvl_RealWork>();
    	
    	int middle = workLoad / 2;
    	
    	int low = middle;
    	int high = workLoad - middle;
    	
    	AParallelismLvl_RealWork subtask1 = new AParallelismLvl_RealWork(low);
        AParallelismLvl_RealWork subtask2 = new AParallelismLvl_RealWork(high);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

    
	private Integer doIt(int sleep) {
		long sum = 0;
		long val = 50_000L * sleep;
		for (int i=0; i< val; i++) {
			sum = sum + i; 
		}
		return sleep;
	}
	
}
