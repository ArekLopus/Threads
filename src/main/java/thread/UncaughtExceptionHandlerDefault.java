package thread;

//You can install a default handler for all threads with the static method Thread .setDefaultUncaughtExceptionHandler().
//-If you don’t install a default handler, the default handler is null. 
//However, if you don’t install a handler for an individual thread, the handler is the thread’s ThreadGroup object.
public class UncaughtExceptionHandlerDefault {
	
	{
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("DefaultUncaughtExceptionHandler: "+t.getName()+", Throwable: "+e.getClass());
			}
		});
		//Or
		Thread.setDefaultUncaughtExceptionHandler( (t, e) -> {
			System.out.println("DefaultUncaughtExceptionHandler: "+t.getName()+", Throwable: "+e.getClass());
		});
		
	}
	
	public UncaughtExceptionHandlerDefault() {
		
		Runnable r = () -> {throw new RuntimeException(); };
		Thread th = new Thread(r);
		
		th.start();
		
		throw new RuntimeException();
	}

	public static void main(String[] args) {
		new UncaughtExceptionHandlerDefault();
	}

}
