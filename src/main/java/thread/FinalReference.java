package thread;

import java.util.HashMap;
import java.util.Map;

//Other threads get to see the accounts variable after the constructor has finished. 
//Without using final, there would be no guarantee that other threads would see the updated value of accounts—they might all see null, not the constructed HashMap.
public class FinalReference {
	
	private int counter = 0;
	
	//final Map<String, Integer> accounts = new HashMap<>();
	Map<String, Integer> accounts = new HashMap<>();
	
	public FinalReference() throws InterruptedException {
		
		Runnable r = () -> {
			//synchronized (accounts) {
				counter++;
				System.out.print(counter+", ");
				accounts.put("1", counter);
			//}
		};
		
		for(int i=0; i<100; i++) {
			new Thread(r).start();
		}

		Thread.sleep(50);
		System.out.println("\n"+accounts.get("1"));
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		new FinalReference();
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
}
