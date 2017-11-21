package threads.cF.usage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Shop2Find1stream {
	
	List<Shop2> shops = Arrays.asList(new Shop2("Shop1"), new Shop2("Shop2"), new Shop2("Shop3"), new Shop2("Shop4"), new Shop2("Shop5"));
	//List<Shop2> shops = Arrays.asList(new Shop2("Shop1"), new Shop2("Shop2"), new Shop2("Shop3"), new Shop2("Shop4"), new Shop2("Shop5"), new Shop2("Shop6"), new Shop2("Shop7"), new Shop2("Shop8"));
	
	public Shop2Find1stream() throws InterruptedException, ExecutionException {
		
		long start = System.nanoTime();
		System.out.println(findPrices("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("\nDone in " + duration + " msecs");
		
	}

	public List<String> findPrices(String product) {
	    return shops.stream()
	    		.map( shop -> shop.getPrice(product))	//→ getting String (like BestPrice:123.26:GOLD)
	    	    .map(Quote::parse)						//→ obj Quote (parsed: shopName, price, discountCode)
	    	    .map(Discount::applyDiscount)			//→ Produces String with the name of the shop and final price
	    	    .collect(Collectors.toList());
	}
	
	public static void main(String[] args) throws Exception {
		new Shop2Find1stream();
	}
	
}
