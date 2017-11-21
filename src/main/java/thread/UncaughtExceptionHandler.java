package thread;

//You can install a handler into any thread with the setUncaughtExceptionHandler().
public class UncaughtExceptionHandler {
	

	public UncaughtExceptionHandler() throws InterruptedException {

		Runnable r = () -> { throw new RuntimeException(); };
		Thread th = new Thread(r);
		
		th.setUncaughtExceptionHandler( (t, e) -> { 
			System.out.println("t.setUncaughtExceptionHandler(): "+t.getName()+", Throwable1: "+e.getClass());
		});
		
		th.start();
		
		Thread.sleep(60);
		System.out.println();
		
		Thread th2 = new Thread(r);
		th2.start();
		
	}

	public static void main(String[] args) throws Exception {
		new UncaughtExceptionHandler();
	}

}
