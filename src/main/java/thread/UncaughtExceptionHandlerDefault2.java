package thread;

public class UncaughtExceptionHandlerDefault2 {
	
	Thread.UncaughtExceptionHandler eh = (t, e) -> {
		System.out.println("DefaultUncaughtExceptionHandler: "+t.getName()+", Throwable: "+e.getClass());
	}; 
	
	public UncaughtExceptionHandlerDefault2() {
		Thread.setDefaultUncaughtExceptionHandler(eh);
	
		Runnable r = () -> {throw new RuntimeException(); };
		Thread th = new Thread(r);
		
		th.start();
		
		throw new RuntimeException();
	}

	public static void main(String[] args) {
		new UncaughtExceptionHandlerDefault2();
	}

}
