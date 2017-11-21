package thread;

public class ThreadCreate implements Runnable {

	public ThreadCreate() throws InterruptedException {
		
		Runnable r = () -> {
			System.out.println(Thread.currentThread().getName()+"\tRunnable lambda");
		};
		
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+"\tAnonymous class implements Runnable");
			}
		};
		
		new Thread(r).start();
		new Thread(r2).start();
		new Thread(this).start();
		
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"\tClass implements Runnable\t\t");
	}
	
	
	
	public static void main(String[] args) throws Exception {
		new ThreadCreate();
	}
	
}
