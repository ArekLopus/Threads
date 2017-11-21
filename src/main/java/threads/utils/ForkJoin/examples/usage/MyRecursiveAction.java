package threads.utils.ForkJoin.examples.usage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveAction extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	
	static int counter = 0;
	int workLoad = 0;
	
    public MyRecursiveAction(int workLoad) {
        this.workLoad = workLoad;
    }

    protected void compute() {

    	if( workLoad > 1) {
      		 
    		List<MyRecursiveAction> subtasks = new ArrayList<MyRecursiveAction>();
    		subtasks.addAll(createSubtasks());

    		RecursiveTask.invokeAll(subtasks);
    		
    	} else {
    		doIt(100);
    	}
    }
    
    private List<MyRecursiveAction> createSubtasks() {
        
    	List<MyRecursiveAction> subtasks = new ArrayList<MyRecursiveAction>();
    	
    	int middle = workLoad / 2;
    	
    	int low = middle;
    	int high = workLoad - middle;
    	
    	MyRecursiveAction subtask1 = new MyRecursiveAction(low);
        MyRecursiveAction subtask2 = new MyRecursiveAction(high);
        
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        return subtasks;
    }

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4*400);
		
		long start = System.nanoTime();

		MyRecursiveAction myRecursiveTask = new MyRecursiveAction(400);
		forkJoinPool.invoke(myRecursiveTask);

		System.out.println("Done in "+((System.nanoTime() - start) / 1_000_000)+" ms");
		System.out.println("Counter: "+counter);
	}
	
	private Integer doIt(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Sleeps: "+sleep+", "+Thread.currentThread().getName());
		counter++;
		return sleep;
	}
	
}
