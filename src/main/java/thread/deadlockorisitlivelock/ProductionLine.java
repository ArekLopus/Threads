package thread.deadlockorisitlivelock;

//When checkin jstack -> Threads are WAITING, not blocked

//"Worker Thread" #11 prio=5 os_prio=0 tid=0x0000000018c70000 nid=0x1abc in Object.wait() [0x000000001945f000]
//		   java.lang.Thread.State: WAITING (on object monitor)
//		        at java.lang.Object.wait(Native Method)
//		        - waiting on <0x00000000d5767510> (a thread.deadlockorisitlivelock.Bin)
//		        at java.lang.Object.wait(Object.java:502)
//		        at thread.deadlockorisitlivelock.Bin.getContents(Bin.java:26)
//		        - locked <0x00000000d5767510> (a thread.deadlockorisitlivelock.Bin)
//		        at thread.deadlockorisitlivelock.Worker.run(Worker.java:17)

//Uncomment notifyAll() w Bin to remove deadlocks
public class ProductionLine {
    
	public static void main(String[] args) {
        
    	Bin bin = new Bin();  // create a bin
        Factory f1 = new Factory(bin, 1);  // create factory
        Worker w1 = new Worker(bin, 1);    // create worker
        
        System.out.println("\nFactory thread is " + f1.getName());
        System.out.println("Worker thread is " + w1.getName() + "\n");
        
        System.out.println("Starting production line...\n");

        f1.start();  // start factory thread
        w1.start();  // start worker thread
    }
}
