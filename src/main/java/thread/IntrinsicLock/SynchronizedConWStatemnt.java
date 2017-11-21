package thread.IntrinsicLock;

public class SynchronizedConWStatemnt {
	
	private int counter = 1;
	
	public SynchronizedConWStatemnt() throws InterruptedException {
		withLock();
	}
	
	private void withLock() {
		Runnable r = () -> {
			synchronized(this) {
				int c = getCounter();
				if(c == 2) {
					notifyAll();
				}
				c++;
				System.out.println("Counter="+counter+", "+Thread.currentThread().getName());
				setCounter(c);
			}
		};
		
		Runnable r2 = () -> {
			synchronized (this) {
				if (getCounter() <2) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("-+= Activated after counter reached 2! =+-"+Thread.currentThread().getName());
		};

		Thread th = new Thread(r2);
		th.setPriority(Thread.MAX_PRIORITY);
		th.start();
		
		for(int i=0; i<8; i++) {
			new Thread(r).start();
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new SynchronizedConWStatemnt();
	}
	
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
	
}
