package thread;

//Threads can be in one of six states:	•New   •Runnable   •Blocked   •Waiting   •Timed waiting   • Terminated
public class ThreadName {

	public ThreadName() throws InterruptedException {
		
		Runnable r = () -> {
			System.out.println("Thread Name: "+Thread.currentThread().getName());
		};
		
		Thread t = new Thread(r);
		t.start();
		
		Thread t2 = new Thread(r);
		t2.setName("This is my Thread");
		t2.start();
		
		Thread.sleep(50);
		
		System.out.println("Main Thread Name: "+Thread.currentThread().getName());
		
		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new ThreadName();
	}

}
