package thread.IntrinsicLock;

public class Synchronized {
	
	int counter = 0;
	
	public Synchronized() throws InterruptedException {
		
		Runnable r = () -> {
			updateCounter();
			//updateCounterSync();
		};
		
		for(int i=0; i<100; i++) {
			Thread t = new Thread(r);
			t.start();
		}
		
		Thread.sleep(100);
		
		System.out.println("Counter is: "+counter);
	}
	
	synchronized public void updateCounterSync() {
		updateCounter();
	}
	
	public void updateCounter() {
		try {
			int c = (int)Math.pow(counter, 1);
			c = c+1;
			c = Math.addExact(c, 1);
			int c2 = c-1;
			Thread.sleep(0);
			System.out.print(c2+", ");
			setCounter(c2);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setCounter(int counter) {
		this.counter = counter;
	}

	public static void main(String[] args) throws Exception {
		new Synchronized();
	}

}
