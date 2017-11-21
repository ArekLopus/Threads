package thread.IntrinsicLock;


public class SynchronizedStatement {
	
	private int counter = 1;
	private Object syncOnMe = new Object();
	
	public SynchronizedStatement() throws InterruptedException {
		
		System.out.println("Without Lock:");
		withoutLock();
		Thread.sleep(500);
		System.out.println("\nWith Lock:");
		counter = 1;
		withLock();
		
	}
	
	private void withoutLock() {
		Runnable r = () -> {
			int c = getCounter();
			c++;
			System.out.print(counter+", ");
			setCounter(c);
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	private void withLock() {
		Runnable r = () -> {
			//synchronized(this) {
			synchronized(syncOnMe) {
				int c = getCounter();
				c++;
				System.out.print(counter+", ");
				setCounter(c);
			}
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new SynchronizedStatement();
	}
	
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
