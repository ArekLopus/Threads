package thread;

//If a thread is blocked, it cannot check the interrupted status. This is where the InterruptedException comes in.
//When the interrupt() is called on a thread that blocks on a call such as sleep or wait,
//the blocking call is terminated by an InterruptedExc.
public class Interrupt1 {

	public Interrupt1() {
		
		Runnable r = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				////By convention, any method that exits by throwing an InterruptedException clears interrupt status when it does so.
				System.out.println("InterruptedExc has been thrown");
				System.out.println("Is interrupted?: "+Thread.currentThread().isInterrupted());
			}
		};
				
		Thread t = new Thread(r);
		t.start();

		t.interrupt();
		
		System.out.println();
	}
	
	
	
	public static void main(String[] args) {
		new Interrupt1();
	}

}
