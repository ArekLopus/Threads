package thread;

//Threads can be in one of six states:	•New   •Runnable   •Blocked   •Waiting   •Timed waiting   • Terminated
public class ThreadDaemon {

	public ThreadDaemon() throws InterruptedException {
		
		Runnable r = () -> {
			System.out.println("Is Daemon?: "+Thread.currentThread().isDaemon());
		};
		
		Thread t = new Thread(r);
		t.start();
		
		Thread t2 = new Thread(r);
		t2.setDaemon(true);;
		t2.start();
		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new ThreadDaemon();
	}

}
