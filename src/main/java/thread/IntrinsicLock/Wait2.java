package thread.IntrinsicLock;

//!!A thread can only call wait(), notifyAll(), or notify() in sychronized mathod / block
//otherwise java.lang.IllegalMonitorStateException
public class Wait2 {
	
	private Object lock = new Object();
	private boolean check = false;
	
	public Wait2() throws InterruptedException {
		withLock();
	}
	
	private void withLock() {
		Runnable r1 = () -> incCunter();
		Runnable r2 = () -> checkIf2();

		new Thread(r1).start();
		new Thread(r2).start();
	}
	
	
	private void incCunter() {
		synchronized (lock) {
			try {
				Thread.sleep(700);
				check = true;
				lock.notify();		//notify wait()
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

	private void checkIf2() {
		synchronized (lock) {
			while (check != true) {
				try {
					lock.wait();	//waits until notify()/notifyAll() called from another thread
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			System.out.println("wait() was activated by notify(), "+Thread.currentThread().getName());			
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Wait2();
	}
	
}
