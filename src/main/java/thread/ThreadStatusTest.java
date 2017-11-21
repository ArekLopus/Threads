package thread;

//Threads can be in one of six states:	•New   •Runnable   •Blocked   •Waiting   •Timed waiting   • Terminated
public class ThreadStatusTest {

	public ThreadStatusTest() throws InterruptedException {
		
		Runnable r = () -> {
			System.out.println(Thread.currentThread().getState());
			Thread.currentThread().interrupt();
			
		};
		
		Thread t = new Thread(r);
		System.out.println(t.getState());
		t.start();
		
		Thread.sleep(100);
		
		System.out.println(t.getState());
		
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		new ThreadStatusTest();
	}

}
