package threads.cF.usage;

import java.util.concurrent.ThreadLocalRandom;

public class Shop3 {
	
	private String name;
		
	public Shop3() {}
	public Shop3(String name) {
		this.name = name;
	}
	
	public String getPrice(String product) {
	    double price = calculatePrice(product);
	    Discount.Code code = Discount.Code.values()[ThreadLocalRandom.current().nextInt(Discount.Code.values().length)];
	    return String.format("%s:%.2f:%s", name, price, code);
	}
	
	private double calculatePrice(String product) {
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
		try {
			Thread.sleep((tlr.nextInt(15)+1)*100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    return tlr.nextDouble() * product.charAt(0) + product.charAt(1);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
