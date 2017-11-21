package thread;

//-An interrupt is an indication to a thread that it should stop what it is doing and do something else.
//It's up to the programmer to decide exactly how a thread responds to an interrupt, but it is very common to terminate. 
public class Interrupt {

	public Interrupt() {
		
		Runnable r = () -> {
			
			try {
			
				while (!Thread.currentThread().isInterrupted() ) {
					Thread.sleep(100);
					print("Is interrupted? ", Thread.currentThread().isInterrupted(), Thread.currentThread());
					Thread.currentThread().interrupt();
					print("Is interrupted? ", Thread.currentThread().isInterrupted(), Thread.currentThread());
					
					//If you call the sleep() when the interrupted status is set, it doesn’t sleep.
					//Instead, it clears the status (!) and throws an InterruptedException.
					Thread.sleep(100);
					
			    }
			} catch(InterruptedException e) {
				print("Is interrupted? ", Thread.currentThread().isInterrupted(), Thread.currentThread());
				e.printStackTrace();
			} 
		};
				
		new Thread(r).start();
		
	}
	
	private void print(String text, Object a, Thread t) {
		System.out.println(text+a+", "+t.getName());
	}
	
	
	public static void main(String[] args) {
		new Interrupt();
	}

}
