package threads.cF.usage;

import java.util.concurrent.ThreadLocalRandom;

public class Shop2 {
	
	private String name;
		
	public Shop2() {}
	public Shop2(String name) {
		this.name = name;
	}
	
	public String getPrice(String product) {
	    double price = calculatePrice(product);
	    Discount.Code code = Discount.Code.values()[ThreadLocalRandom.current().nextInt(Discount.Code.values().length)];
	    return String.format("%s:%.2f:%s", name, price, code);
	}
	
	private double calculatePrice(String product) {
	    try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    return ThreadLocalRandom.current().nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
