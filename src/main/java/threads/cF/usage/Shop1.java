package threads.cF.usage;

import java.util.concurrent.ThreadLocalRandom;

public class Shop1 {
	
	private String name;
		
	public Shop1() {}
	public Shop1(String name) {
		this.name = name;
	}
		
	public double getPrice(String item) {
		try {
//			int size = 80_000_000;
//			int[] arr = new int[size];
//			for(int j=0; j<2; j++)
//				for(int i=0; i<size; i++)
//					arr[i] = i;
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ThreadLocalRandom.current().nextDouble(10)+1)*10;
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
