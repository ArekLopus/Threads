package thread.clientSideLocking;

import java.util.Vector;

//If you have 2 or more synchronized methods and you want to run them as an atomic operation you need to synchronize this 
//operation becasue a thread can be preempted between those method invocations.
public class ClientSideLocking {
	
	private Vector<Integer> v = new Vector<>(10);
	
	public ClientSideLocking() throws InterruptedException {
		
		v.add(0);
		withoutClientLock();
		Thread.sleep(500);
		System.out.println("without client sync: "+v.get(0));
		
		v.set(0, 0);
		withClientLock();
		Thread.sleep(500);
		System.out.println("with client sync: "+v.get(0));

	}
	
	private void withoutClientLock() {
		for(int i=0;i<100;i++) {
			new Thread( () -> { 
				v.set(0, (v.get(0)+1));
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				v.set(0, (v.get(0)+1));
				v.set(0, (v.get(0)+1));
			} ).start();

		}
	}
	
	private void withClientLock() {
		for(int i=0;i<100;i++) {
			new Thread( () -> { 
				synchronized (this) {
					v.set(0, (v.get(0)+1));
					try {
						Thread.sleep(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					v.set(0, (v.get(0)+1));
					v.set(0, (v.get(0)+1));	
				}
			} ).start();
		}
	}

	
	public static void main(String[] args) throws InterruptedException {
		new ClientSideLocking();
	}
	
	
}
