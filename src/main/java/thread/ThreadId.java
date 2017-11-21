package thread;

//Threads can be in one of six states:	•New   •Runnable   •Blocked   •Waiting   •Timed waiting   • Terminated
public class ThreadId {

	public ThreadId() throws InterruptedException {
		
		Runnable r = () -> {
			System.out.println("Thread Id: "+Thread.currentThread().getId());
		};
		
		Thread t = new Thread(r);
		t.start();
		
		Thread t2 = new Thread(r);
		t2.start();
		
		System.out.println("Main Thread Id: "+Thread.currentThread().getId());
		
		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new ThreadId();
	}

}
