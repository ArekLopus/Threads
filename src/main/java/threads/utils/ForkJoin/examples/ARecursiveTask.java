package threads.utils.ForkJoin.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ARecursiveTask extends RecursiveTask<Long> {
	private static final long serialVersionUID = 1L;
	
	private long workLoad = 0;

    public ARecursiveTask(long workLoad) {
        this.workLoad = workLoad;
    }

    protected Long compute() {

        //if work is above threshold, break tasks up into smaller tasks
        if(this.workLoad > 16) {
            System.out.println("Splitting workLoad : " + this.workLoad);

            List<ARecursiveTask> subtasks = new ArrayList<ARecursiveTask>();
            subtasks.addAll(createSubtasks());

            for(ARecursiveTask subtask : subtasks){
                subtask.fork();
            }

            long result = 0;
            for(ARecursiveTask subtask : subtasks) {
                result += subtask.join();
            }
            return result;

        } else {
            System.out.println("Doing workLoad myself: " + this.workLoad);
            return workLoad;
        }
    }

    private List<ARecursiveTask> createSubtasks() {
        
    	List<ARecursiveTask> subtasks = new ArrayList<ARecursiveTask>();

        ARecursiveTask subtask1 = new ARecursiveTask(this.workLoad / 2);
        ARecursiveTask subtask2 = new ARecursiveTask(this.workLoad / 2);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }


	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		
		ARecursiveTask myRecursiveTask = new ARecursiveTask(128);
		long mergedResult = forkJoinPool.invoke(myRecursiveTask);

		System.out.println("mergedResult = " + mergedResult);  
	}

}
