package thread.IntrinsicLock;

public class SynchronizedCondition {
	
	private int counter = 1;
	
	public SynchronizedCondition() throws InterruptedException {
		withLock();
	}
	
	private void withLock() {
		Runnable r1 = () -> incCunter();
		Runnable r2 = () -> checkIf2();

		Thread th = new Thread(r2);
		th.start();
		
		for(int i=0; i<8; i++) {
			new Thread(r1).start();
		}
	}
	
	
	private synchronized void incCunter() {
		int c = getCounter();
		if(c == 2) {
			notifyAll();
		}
		c++;
		System.out.println("Counter="+counter+", "+Thread.currentThread().getName());
		setCounter(c);
	}

	private synchronized void checkIf2() {
		if (getCounter() <2) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("-+= Activated after counter reached 2! =+-"+Thread.currentThread().getName());
	}
	
	public static void main(String[] args) throws InterruptedException {
		new SynchronizedCondition();
	}
	
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
