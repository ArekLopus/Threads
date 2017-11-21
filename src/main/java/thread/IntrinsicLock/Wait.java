package thread.IntrinsicLock;

//!!A thread can only call wait(), notifyAll(), or notify() in sychronized mathod / block
//otherwise java.lang.IllegalMonitorStateException
public class Wait {
	
	private boolean check = false;
	
	public Wait() throws InterruptedException {
		withLock();
	}
	
	private void withLock() {
		Runnable r1 = () -> incCunter();
		Runnable r2 = () -> checkIf2();

		new Thread(r1).start();
		new Thread(r2).start();
	}
	
	
	private synchronized void incCunter() {
		try {
			Thread.sleep(700);
			check = true;
			notify();		//notify wait()
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private synchronized void checkIf2() {
		while (check != true) {
			try {
				wait();		//waits until notify()/notifyAll() called from another thread
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		System.out.println("wait() was activated by notify(), "+Thread.currentThread().getName());
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Wait();
	}
	
}
