package thread;

public class Interrupt2 {

	public Interrupt2() {
		
		Runnable r = () -> {
			
			System.out.println("Is interrupted? "+Thread.currentThread().isInterrupted());
			Thread.currentThread().interrupt();
			System.out.println("Is interrupted? "+Thread.currentThread().isInterrupted());
			
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		new Thread(r).start();
		
		System.out.println();
	}
	
	
	
	
	public static void main(String[] args) {
		new Interrupt2();
	}

}
