package thread;

//If only 1 thread reads and writes the value of a volatile variable and other threads only read the variable,
//then the reading threads are guaranteed to see the latest value written to the volatile variable.
public class VolatileFields {

    public VolatileFields() throws InterruptedException {
        
    	MyThread t = new MyThread();
        t.start();
    	Thread.sleep(1000);
    	t.runIt = false;
    	System.out.println("runIt is false. "+Thread.currentThread().getName());
        //main thread changes itrunIt field. In the other thread, if volatile, field gets updated while thread in loop.
        
	}
    
    public static void main(String[] args) throws Exception {
    	new VolatileFields();
    }
}

class MyThread extends Thread {
	
	//volatile boolean runIt = true;
	boolean runIt = true;

    public void run() {
        long count=0;
        while (runIt) {
            count++;
        }

        System.out.println("Thread terminated. Count: "+count+". Thread: "+Thread.currentThread().getName());
    }

}
